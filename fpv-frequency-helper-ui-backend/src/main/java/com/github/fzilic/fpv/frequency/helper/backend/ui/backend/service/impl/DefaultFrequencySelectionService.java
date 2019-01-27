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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        .setMaxResults(50)
        .getResultList();

    // final Page<Result> found = resultRepository.findAll(QResult.result.numberOfChannels.eq(pilots.size())
    //         .and(QResult.result.minimumSeparationChannel.goe(minimumSeparation))
    //         .and(QResult.result.minimumSeparationImd.goe(minimumSeparation))
    //         .and(ExpressionUtils.allOf(predicates)),
    //     PageRequest.of(0, 50, Sort.by(Order.desc("minimumSeparationImd"), Order.desc("minimumSeparationChannel"))));


    // return null;
    final List<RecommendationResult> results = found.stream()
        .map(result -> {
          final List<Channel> recommendations = new ArrayList<>(result.getChannels());

          final RecommendationResult build = RecommendationResult.builder()
              .result(result)
              .pilots(pilots.stream()
                  .sorted(Comparator.comparing(Pilot::getOrdinal))
                  .peek(pilot ->
                      pilot.setAvailableChannels(pilot.getAvailableChannels().stream()
                          .filter(channel ->
                              recommendations.stream()
                                  .anyMatch(recommendation ->
                                      recommendation.getId().equals(channel.getId())))
                          .collect(Collectors.toList())))
                  .map(pilot ->
                      Pilot.builder()
                          .nickname(pilot.getNickname())
                          .ordinal(pilot.getOrdinal())
                          .availableChannels(pilot.getAvailableChannels())
                          // .recommendedChannel(channel)
                          .build())
                  .collect(Collectors.toList()))
              .build();


          build.getPilots().stream()
              .filter(pilot ->
                  pilot.getAvailableChannels().size() == 1)
              .forEach(pilot -> {
                recommendations.remove(pilot.getAvailableChannels().get(0));
                pilot.setRecommendedChannel(pilot.getAvailableChannels().get(0));
                pilot.setAvailableChannels(null);
              });

          int maxAttempts = 500;
          while (build.getPilots().stream().anyMatch(pilot -> pilot.getRecommendedChannel() == null) && maxAttempts > 0) {
            build.getPilots().stream()
                .filter(pilot ->
                    pilot.getRecommendedChannel() == null)
                .findAny()
                .ifPresent(pilot -> {

                  pilot.getAvailableChannels().stream()
                      .filter(channel ->
                          build.getPilots().stream()
                              .filter(other ->
                                  !other.equals(pilot) && other.getAvailableChannels() != null)
                              .map(Pilot::getAvailableChannels)
                              .flatMap(Collection::stream)
                              .noneMatch(other -> channel.getId().equals(other.getId())))
                      .findAny()
                      .ifPresent(channel -> {
                        pilot.setRecommendedChannel(channel);
                        pilot.setAvailableChannels(null);
                        recommendations.remove(channel);
                      });

                  if (pilot.getRecommendedChannel() == null) {
                    pilot.setRecommendedChannel(recommendations.get(0));
                    recommendations.remove(pilot.getRecommendedChannel());
                  }

                });

            maxAttempts--;
          }

          return build;
        })
        .filter(recommendationResult ->
            recommendationResult.getPilots().stream().filter(Objects::nonNull).count() == pilots.size())
        .collect(Collectors.toList());


    return results;
  }

}
