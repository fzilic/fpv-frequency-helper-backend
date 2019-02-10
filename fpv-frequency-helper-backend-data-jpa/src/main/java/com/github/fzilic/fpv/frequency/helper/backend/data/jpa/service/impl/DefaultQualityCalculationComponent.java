package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.impl;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.QualityCalculationComponent;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.data.QualityResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
public class DefaultQualityCalculationComponent implements QualityCalculationComponent {

  @Async
  @Override
  public ListenableFuture<QualityResult> calculate(final List<Channel> channels) {
    log.debug("Calculating {}", channels.stream().map(Channel::getFrequency).map(Objects::toString).collect(Collectors.joining(",")));

    final List<Integer> separations = new ArrayList<>(channels.size() - 1);

    IntStream.range(0, channels.size() - 1)
        .forEach(value ->
            separations.add(Math.abs(channels.get(value + 1).getFrequency() - channels.get(value).getFrequency())));

    final Set<Integer> imdFrequencies = new TreeSet<>(Integer::compareTo);

    for (int i = 0; i < channels.size(); i++) {
      for (int j = i + 1; j < channels.size(); j++) {
        final Integer f1 = channels.get(i).getFrequency();
        final Integer f2 = channels.get(j).getFrequency();
        log.debug("F1 {} F2 {}", f1, f2);

        final int imd1 = f1 * 2 - f2;
        final int imd2 = f2 * 2 - f1;
        log.debug("IMD {} {}", imd1, imd2);
        imdFrequencies.add(imd1);
        imdFrequencies.add(imd2);
      }
    }

    final List<Integer> channelsToImdSeparations = channels.stream()
        .map(channel ->
            imdFrequencies.stream()
                .map(integer ->
                    Math.abs(channel.getFrequency() - integer)))
        .flatMap(integerStream -> integerStream)
        .collect(Collectors.toList());

    return new AsyncResult<>(QualityResult.builder()
        .minimumSeparationChannel(separations.stream().min(Integer::compareTo).orElse(0))
        .averageSeparationChannel(separations.stream().mapToDouble(value -> value).average().orElse(0.0))
        .minimumSeparationImd(channelsToImdSeparations.stream().min(Integer::compareTo).orElse(0))
        .averageSeparationImd(channelsToImdSeparations.stream().mapToDouble(value -> value).average().orElse(0.0))
        .build());
  }
}
