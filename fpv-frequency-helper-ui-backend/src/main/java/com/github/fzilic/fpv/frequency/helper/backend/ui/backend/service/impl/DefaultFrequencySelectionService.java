package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.service.impl;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository.ResultRepository;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common.Pilot;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common.RecommendationResult;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.exception.ConflictingPilotsException;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.service.FrequencySelectionService;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.util.QueryUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DefaultFrequencySelectionService implements FrequencySelectionService {

  private final ResultRepository resultRepository;

  private final EntityManager entityManager;

  @Autowired
  public DefaultFrequencySelectionService(final ResultRepository resultRepository, final EntityManager entityManager) {
    this.resultRepository = resultRepository;
    this.entityManager = entityManager;
  }

  @SuppressWarnings("Duplicates")
  @Override
  @Transactional
  public List<RecommendationResult> recommendChannels(final List<Pilot> pilots, final Integer minimumSeparation) {
    if (pilots.size() < 2 || pilots.size() > 6) {
      throw new IllegalArgumentException();
    }

    // preserve pilot order
    IntStream.range(0, pilots.size())
        .forEach(value -> {
          pilots.get(value).setOrdinal(value);
          pilots.get(value).getAvailableChannels()
              .sort(Comparator.comparing(Channel::getFrequency));
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

            if (Math.abs(pilot1.getAvailableChannels().get(0).getFrequency() - pilot2.getAvailableChannels().get(0).getFrequency()) <= minimumSeparation) {
              conflictingPilots.add(new ImmutablePair<>(pilot1, pilot2));
            }
          });

      if (conflictingPilots.size() > 0) {
        log.warn("Conflicting single channel pilots {}", conflictingPilots);
        throw new ConflictingPilotsException(conflictingPilots);
      }
    }

    final TypedQuery<Result> query = entityManager.createQuery(QueryUtil.query(pilots.size())
        , Result.class)
        .setParameter("numberOfChannels", pilots.size())
        .setParameter("minimumSeparationChannel", minimumSeparation)
        .setParameter("minimumSeparationImd", minimumSeparation)
        .setParameter("p0", pilots.get(0).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()))
        .setParameter("p1", pilots.get(1).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()));

    if (pilots.size() >= 3) {
      query.setParameter("p2", pilots.get(2).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()));
    }
    if (pilots.size() >= 4) {
      query.setParameter("p3", pilots.get(3).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()));
    }
    if (pilots.size() >= 5) {
      query.setParameter("p4", pilots.get(4).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()));
    }
    if (pilots.size() == 6) {
      query.setParameter("p5", pilots.get(5).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()));
    }

    final List<Result> found = query
        .setHint("org.hibernate.cacheable", true)
        .setMaxResults(50)
        .getResultList();


    return found.stream()
        .map(result -> {
          final List<Channel> recommendations = new ArrayList<>(result.getChannels());

          final List<Pilot> pilotsForRecommendation = pilots.stream()
              .map(pilot -> Pilot.builder()
                  .nickname(pilot.getNickname())
                  .ordinal(pilot.getOrdinal())
                  .availableChannels(pilot.getAvailableChannels().stream()
                      .filter(channel ->
                          recommendations.stream()
                              .anyMatch(recommended ->
                                  recommended.getId().equals(channel.getId())))
                      .collect(Collectors.toList()))
                  .build())
              .collect(Collectors.toList());

          final int maxIterations = recommendations.size() * pilotsForRecommendation.stream()
              .map(Pilot::getAvailableChannels)
              .mapToInt(List::size)
              .sum();

          int limit = maxIterations;

          while (!recommendations.isEmpty()) {

            pilotsForRecommendation.forEach(pilot -> {
              if (pilot.getAvailableChannels().size() == 1) {
                pilot.setRecommendedChannel(pilot.getAvailableChannels().get(0));
                recommendations.removeIf(recommendation ->
                    recommendation.getId().equals(pilot.getRecommendedChannel().getId()));
              }
            });

            pilotsForRecommendation.stream()
                .filter(p -> p.getRecommendedChannel() == null)
                .forEach(pilot ->
                    pilot.setAvailableChannels(pilot.getAvailableChannels().stream()
                        .filter(channel -> recommendations.stream()
                            .anyMatch(recommendation -> recommendation.getId().equals(channel.getId())))
                        .collect(Collectors.toList())));


            final Set<Channel> distinct = pilotsForRecommendation.stream()
                .filter(pilot ->
                    pilot.getRecommendedChannel() == null)
                .map(Pilot::getAvailableChannels)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

            if (pilotsForRecommendation.stream()
                .noneMatch(pilot ->
                    pilot.getRecommendedChannel() == null
                        && pilot.getAvailableChannels().size() != distinct.size())) {

              pilotsForRecommendation.stream()
                  .filter(pilot ->
                      pilot.getRecommendedChannel() == null)
                  .findAny()
                  .ifPresent(pilot -> {

                    pilot.setRecommendedChannel(pilot.getAvailableChannels().get(0));
                    recommendations.removeIf(recommendation -> recommendation.getId().equals(pilot.getRecommendedChannel().getId()));
                  });
            }
            else {

              pilotsForRecommendation.stream()
                  .filter(pilot ->
                      pilot.getRecommendedChannel() == null
                          && pilot.getAvailableChannels().size() == distinct.size())
                  .findAny()
                  .ifPresent(pilot -> {
                    pilot.getAvailableChannels().stream()
                        .filter(available ->
                            pilotsForRecommendation.stream()
                                .filter(other ->
                                    other.getRecommendedChannel() == null
                                        && other.getAvailableChannels().size() != distinct.size())
                                .map(Pilot::getAvailableChannels)
                                .flatMap(Collection::stream)
                                .distinct()
                                .noneMatch(other -> other.getId().equals(available.getId())))
                        .findAny()
                        .ifPresent(channel -> {
                          pilot.setRecommendedChannel(channel);
                          recommendations.removeIf(recommendation ->
                              recommendation.getId().equals(channel.getId()));
                        });

                    if (pilot.getRecommendedChannel() == null) {
                      pilot.setRecommendedChannel(pilot.getAvailableChannels().get(0));
                      recommendations.removeIf(recommendation -> recommendation.getId().equals(pilot.getRecommendedChannel().getId()));
                    }
                  });
            }

            limit--;

            if (limit <= 0) {
              log.warn("Failed to resolve {} {} {} {}", maxIterations, result.getFrequencies(), recommendations, pilotsForRecommendation);
              break;
            }
          }


          return RecommendationResult.builder()
              .result(result)
              .pilots(pilotsForRecommendation)
              .build();
        })
        .filter(recommendationResult -> recommendationResult.getPilots().stream()
            .noneMatch(pilot -> pilot.getRecommendedChannel() == null))
        .collect(Collectors.toList());
  }

}
