package com.github.fzilic.fpv.frequency.helper.backend.service.impl;

import com.github.fzilic.fpv.frequency.helper.backend.data.common.Pilot;
import com.github.fzilic.fpv.frequency.helper.backend.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.exception.ConflictingPilotsException;
import com.github.fzilic.fpv.frequency.helper.backend.service.FrequencySelectionService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FrequencySelectionServiceImpl implements FrequencySelectionService {

  @Override
  public List<List<Pilot>> recommendChannels(final Integer lowerSpacing, final Integer upperSpacing, final List<Pilot> pilots) {
    if (pilots.size() < 2) {
      throw new IllegalArgumentException();
    }

    // preserve pilot order
    IntStream.range(0, pilots.size())
        .forEach(value -> {
          pilots.get(value).setOrdinal(value);
          pilots.get(value).getAvailableChannels()
              .sort(Comparator.comparing(Channel::getFrequency, Comparator.reverseOrder()));
        });

    // find pilots with only one channel
    final List<Pilot> singleChannelPilots = pilots.stream()
        .filter(pilot ->
            pilot.getAvailableChannels().size() == 1)
        .sorted(Comparator.comparing(o ->
            o.getAvailableChannels().get(0).getFrequency()))
        .collect(Collectors.toList());

    // verify single channel pilots can fly together
    if (singleChannelPilots.size() > 1) {
      final List<Pair<Pilot, Pilot>> conflictingPilots = new LinkedList<>();

      IntStream.range(0, singleChannelPilots.size() - 1)
          .forEach(value -> {
            final Pilot pilot1 = singleChannelPilots.get(value);
            final Pilot pilot2 = singleChannelPilots.get(value + 1);

            if (pilot1.getAvailableChannels().get(0).getFrequency() + upperSpacing >= pilot2.getAvailableChannels().get(0).getFrequency() - lowerSpacing) {
              conflictingPilots.add(new ImmutablePair<>(pilot1, pilot2));
            }
          });

      if (conflictingPilots.size() > 0) {
        log.warn("Conflicting single channel pilots {}", conflictingPilots);
        throw new ConflictingPilotsException(conflictingPilots);
      }
    }

    // pilots.sort(Comparator.comparing(o -> o.getAvailableChannels().size()));

    // generate all possible recommendations for each pilot
    final List<List<Pilot>> collect = pilots.stream()
        .map(pilot ->
            pilot.getAvailableChannels().stream().map(channel -> {
              final Pilot potentialRecommendation = Pilot.builder()
                  .recommendedChannel(channel).build();
              BeanUtils.copyProperties(pilot, potentialRecommendation, "availableChannels", "recommendedChannel");
              return potentialRecommendation;
            }).collect(Collectors.toList()))
        .collect(Collectors.toList());

    // find all possible combinations for pilots, and find valid ones
    final Set<List<Pilot>> valid = cartesian(collect).parallelStream()
        .filter(combination ->
            isValid(combination, lowerSpacing, upperSpacing))
        .collect(Collectors.toSet());

    // attempt to locate best one
    final SortedMap<Statistics, List<Pilot>> scored = new TreeMap<>((o1, o2) ->
        Double.compare(o2.average / (o2.stdev == 0.0 ? 1: o2.stdev), o1.average / (o1.stdev == 0.0 ? 1: o1.stdev)));

    valid.forEach(candidate -> {
      final List<Integer> separations = new ArrayList<>(candidate.size() - 1);

      IntStream.range(0, candidate.size() - 1)
          .forEach(value ->
              separations.add(Math.abs(candidate.get(value + 1).getRecommendedChannel().getFrequency() - candidate.get(value).getRecommendedChannel().getFrequency())));

      final double stdev = new StandardDeviation().evaluate(separations.stream().mapToDouble(value -> value).toArray());

      separations.stream()
          .mapToDouble(value -> value)
          .average()
          .ifPresent(value ->
              scored.put(Statistics.builder()
                  .average(Math.floor(value))
                  .stdev(stdev)
                  .build(), candidate));
    });

    return scored.entrySet().stream()
        .peek(entry -> log.debug("{} - {}", entry.getKey(), entry.getValue().stream()
            .map(pilot ->
                pilot.getNickname() + " " +
                    pilot.getRecommendedChannel().getBand().getName() +
                    pilot.getRecommendedChannel().getNumber() +
                    " (" + pilot.getRecommendedChannel().getFrequency() + ")")
            .collect(Collectors.toList())))
        .peek(entry ->
            entry.getValue().sort(Comparator.comparing(Pilot::getOrdinal)))
        .map(Entry::getValue)
        .collect(Collectors.toList());
  }

  private boolean isValid(final List<Pilot> combination, final Integer lowerSpacing, final Integer upperSpacing) {
    for (int i = 0; i < combination.size() - 1; i++) {
      final Pilot pilot1 = combination.get(0);
      final Pilot pilot2 = combination.get(1);
      if (pilot1.getRecommendedChannel().getFrequency() + upperSpacing >= pilot2.getRecommendedChannel().getFrequency() - lowerSpacing) {
        return false;
      }
    }
    return true;
  }

  private Set<List<Pilot>> cartesian(final List<List<Pilot>> candidates) {
    return cartesian(candidates.get(0), candidates.subList(1, candidates.size()))
        .parallelStream().peek(pilots ->
            pilots.sort(Comparator.comparing(pilot ->
                pilot.getRecommendedChannel().getFrequency())))
        .distinct()
        .collect(Collectors.toSet());
  }

  private List<List<Pilot>> cartesian(final List<Pilot> head, final List<List<Pilot>> rest) {
    if (rest.size() == 1) {
      final List<List<Pilot>> cartesian = new ArrayList<>();

      head.forEach(pilot ->
          rest.get(0).forEach(pilot1 ->
              cartesian.add(new ArrayList<>(Arrays.asList(pilot, pilot1)))));

      return cartesian;
    }

    final List<List<Pilot>> cartesian = new ArrayList<>();

    head.forEach(pilot ->
        cartesian(rest.get(0), rest.subList(1, rest.size())).forEach(pilots -> {
          final ArrayList<Pilot> e = new ArrayList<>(pilots);
          e.add(pilot);
          cartesian.add(e);
        }));

    return cartesian;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  private static class Statistics {

    private Double average;

    private Double stdev;

    public String toString() {
      return "(avg=" + this.getAverage() + ", stdev=" + this.getStdev() + ")";
    }
  }

}
