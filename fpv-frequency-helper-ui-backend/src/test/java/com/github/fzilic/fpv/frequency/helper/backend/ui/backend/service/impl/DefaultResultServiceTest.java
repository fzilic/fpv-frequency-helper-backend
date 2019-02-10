package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.service.impl;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository.ChannelRepository;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.GeneratorService;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common.Pilot;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
    "spring.liquibase.change-log=classpath:/liquibase.xml"
})
class DefaultResultServiceTest {

  @Autowired
  private GeneratorService generatorService;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private ChannelRepository channelRepository;

  @Test
  void shouldFetchForTwo() {
    generatorService.generate(2, 2, 30, 100);

    final List<Channel> all = channelRepository.findAll().stream().filter(channel -> channel.getBand().getName().contains("A")).collect(Collectors.toList());

    final List<Pilot> pilots = Arrays.asList(Pilot.builder().availableChannels(all).build(),
        Pilot.builder().availableChannels(all).build());

    final List<Result> query = new DefaultResultService(entityManager).query(30, 500, pilots);

    assertThat(query).isNotEmpty();

    final List<Result> oldQuery = entityManager.createQuery(QueryUtil.query(2), Result.class)
        .setParameter("numberOfChannels", 2)
        .setParameter("minimumSeparationChannel", 30)
        .setParameter("minimumSeparationImd", 30)
        .setParameter("p0", pilots.get(0).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()))
        .setParameter("p1", pilots.get(1).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()))
        .setMaxResults(500)
        .getResultList();

    assertThat(oldQuery).isNotEmpty();

    assertThat(query).hasSameSizeAs(oldQuery);

    assertThat(query).containsOnlyElementsOf(oldQuery);
  }

  @Test
  void shouldFetchForThree() {
    generatorService.generate(2, 3, 30, 100);

    final List<Channel> all = channelRepository.findAll().stream().filter(channel -> channel.getBand().getPreselected()).collect(Collectors.toList());

    final List<Pilot> pilots = Arrays.asList(Pilot.builder().availableChannels(all).build(),
        Pilot.builder().availableChannels(all).build(),
        Pilot.builder().availableChannels(all).build());

    final List<Result> query = new DefaultResultService(entityManager).query(30, 5, pilots);

    assertThat(query).isNotEmpty();

    final List<Result> oldQuery = entityManager.createQuery(QueryUtil.query(3), Result.class)
        .setParameter("numberOfChannels", 3)
        .setParameter("minimumSeparationChannel", 30)
        .setParameter("minimumSeparationImd", 30)
        .setParameter("p0", pilots.get(0).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()))
        .setParameter("p1", pilots.get(1).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()))
        .setParameter("p2", pilots.get(2).getAvailableChannels().stream().map(Channel::getId).collect(Collectors.toList()))
        .setMaxResults(5)
        .getResultList();

    assertThat(oldQuery).isNotEmpty();

    assertThat(query).hasSameSizeAs(oldQuery);

    assertThat(query).containsOnlyElementsOf(oldQuery);
  }


  private abstract static class QueryUtil {

    private static String QUERY_2 = "SELECT r " +
        "FROM Result r " +
        "WHERE r.numberOfChannels = :numberOfChannels " +
        "AND r.minimumSeparationChannel >= :minimumSeparationChannel " +
        "AND r.minimumSeparationImd >= :minimumSeparationImd " +
        "AND EXISTS (" +
        "  SELECT c0.id " +
        "  FROM Result r0 " +
        "  INNER JOIN r0.channels cr0 " +
        "  INNER JOIN cr0.channel c0 " +
        "  WHERE c0.id IN :p0 " +
        "  AND r0.numberOfChannels = :numberOfChannels " +
        "  AND r0.id = r.id " +
        "  AND EXISTS ( " +
        "    SELECT c1.id " +
        "    FROM Result r1 " +
        "    INNER JOIN r1.channels cr1 " +
        "    INNER JOIN cr1.channel c1 " +
        "    WHERE c1.id IN :p1 " +
        "    AND r1.numberOfChannels = :numberOfChannels " +
        "    AND c1.id NOT IN ( c0.id ) " +
        "    AND r1.id = r.id " +
        "  ) " +
        ") " +
        "ORDER BY r.minimumSeparationImd DESC," +
        "         r.minimumSeparationChannel DESC";

    private static String QUERY_3 = "SELECT r " +
        "FROM Result r " +
        "WHERE r.numberOfChannels = :numberOfChannels " +
        "AND r.minimumSeparationChannel >= :minimumSeparationChannel " +
        "AND r.minimumSeparationImd >= :minimumSeparationImd " +
        "AND EXISTS (" +
        "  SELECT c0.id " +
        "  FROM Result r0 " +
        "  INNER JOIN r0.channels cr0 " +
        "  INNER JOIN cr0.channel c0 " +
        "  WHERE c0.id IN :p0 " +
        "  AND r0.numberOfChannels = :numberOfChannels " +
        "  AND r0.id = r.id " +
        "  AND EXISTS ( " +
        "    SELECT c1.id " +
        "    FROM Result r1 " +
        "    INNER JOIN r1.channels cr1 " +
        "    INNER JOIN cr1.channel c1 " +
        "    WHERE c1.id IN :p1 " +
        "    AND r1.numberOfChannels = :numberOfChannels " +
        "    AND c1.id NOT IN ( c0.id ) " +
        "    AND r1.id = r.id " +
        "    AND EXISTS ( " +
        "      SELECT c2.id " +
        "      FROM Result r2 " +
        "      INNER JOIN r2.channels cr2 " +
        "      INNER JOIN cr2.channel c2 " +
        "      WHERE c2.id IN :p2 " +
        "      AND r2.numberOfChannels = :numberOfChannels " +
        "      AND c2.id NOT IN ( c0.id ) " +
        "      AND c2.id NOT IN ( c1.id ) " +
        "      AND r2.id = r.id " +
        "    ) " +
        "  ) " +
        ") " +
        "ORDER BY r.minimumSeparationImd DESC," +
        "         r.minimumSeparationChannel DESC";


    public static String query(final Integer size) {
      switch (size) {
        case 2:
          return QUERY_2;
        case 3:
          return QUERY_3;
        default:
          throw new IllegalArgumentException();
      }
    }

    private QueryUtil() {
    }
  }
}