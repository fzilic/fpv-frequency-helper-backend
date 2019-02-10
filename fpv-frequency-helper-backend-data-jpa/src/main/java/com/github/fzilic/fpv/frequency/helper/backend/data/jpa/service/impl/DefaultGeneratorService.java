package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.impl;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.ResultChannel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository.ChannelRepository;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.GeneratorService;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.QualityCalculationComponent;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.ResultPersistingService;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultGeneratorService implements GeneratorService {

  private final ChannelRepository channelRepository;

  private final ResultPersistingService service;

  private final QualityCalculationComponent qualityCalculationComponent;

  private static void combination(final List<Channel> channels, final Channel[] data,
      final Integer start, final Integer end, final Integer index, final Integer count,
      final Consumer<List<Channel>> whenFound) {

    if (index.equals(count)) {
      whenFound.accept(Stream.of(data).sorted(Comparator.comparing(Channel::getFrequency)).collect(Collectors.toList()));
    }
    else {
      for (int i = start; i <= end && end - i + 1 >= count - index; i++) {
        data[index] = channels.get(i);
        combination(channels, data, i + 1, end, index + 1, count, whenFound);
      }
    }
  }

  public static void combinations(final List<Channel> channels, final Integer pilotCount, final Consumer<List<Channel>> whenFound) {
    final Channel[] data = new Channel[pilotCount];
    combination(channels, data, 0, channels.size() - 1, 0, pilotCount, whenFound);
  }


  @Autowired
  public DefaultGeneratorService(final ChannelRepository channelRepository, final ResultPersistingService service,
      final QualityCalculationComponent qualityCalculationComponent) {
    this.channelRepository = channelRepository;
    this.service = service;
    this.qualityCalculationComponent = qualityCalculationComponent;
  }


  @Override
  public void generate(final Integer pilotCount, final Integer maxPilotCount, final Integer minFrequencySeparation, final Integer batchSize) {
    final List<Channel> channels = channelRepository.findAllByBand_PreselectedIsTrue();

    if (pilotCount < 2) {
      return;
    }

    log.info("Persisting combinations for {}-{} pilots with {} channels", pilotCount, maxPilotCount, channels.size());

    final Set<List<Channel>> candidates = new HashSet<>();
    final Set<String> knownFrequencies = service.findDistinctFrequencies();

    IntStream.range(pilotCount, maxPilotCount + 1).forEach(value -> {
      log.info("Persisting combinations for {} pilots", value);

      combinations(channels, value, combination -> {
        final String frequencies = combination.stream().map(Channel::getFrequency).map(Object::toString).collect(Collectors.joining(","));
        log.debug("Combination {}", frequencies);

        if (!knownFrequencies.contains(frequencies)) {
          knownFrequencies.add(frequencies);
          candidates.add(combination);
        }

        if (candidates.size() >= batchSize) {
          saveBatch(candidates, minFrequencySeparation);
        }

      });
    });

    if (!candidates.isEmpty()) {
      log.info("Batch not empty at end");
      saveBatch(candidates, minFrequencySeparation);
    }

    log.info("DONE");
  }

  private void saveBatch(final Set<List<Channel>> candidates, final Integer minFrequencySeparation) {
    log.info("Persisting batch {}", candidates.size());
    final CountDownLatch latch = new CountDownLatch(candidates.size());

    final List<Result> results = new CopyOnWriteArrayList<>();

    candidates.forEach(candidate ->
        qualityCalculationComponent.calculate(candidate).addCallback(quality -> {

          if (quality != null
              && quality.getMinimumSeparationChannel() > minFrequencySeparation
              && quality.getMinimumSeparationImd() > minFrequencySeparation) {

            final Result result = Result.builder()
                .numberOfChannels(candidate.size())
                .frequencies(candidate.stream().map(Channel::getFrequency).map(Object::toString).collect(Collectors.joining(",")))
                .minimumSeparationChannel(quality.getMinimumSeparationChannel())
                .averageSeparationChannel(quality.getAverageSeparationChannel())
                .minimumSeparationImd(quality.getMinimumSeparationImd())
                .averageSeparationImd(quality.getAverageSeparationImd())
                .build();

            result.setChannels(candidate.stream()
                .map(channel ->
                    ResultChannel.builder()
                        .result(result)
                        .channel(channel)
                        .minSeparationOtherChannels(candidate.stream()
                            .filter(c ->
                                !channel.getId().equals(c.getId()))
                            .mapToInt(c ->
                                Math.abs(c.getFrequency() - channel.getFrequency()))
                            .min()
                            .orElse(0))
                        .build())
                .collect(Collectors.toList()));

            results.add(result);
          }

          latch.countDown();
        }, ex -> {
          log.error("Something that should not happen", ex);
          latch.countDown();
        }));

    try {
      latch.await();
      service.saveAll(results);
      candidates.clear();
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
