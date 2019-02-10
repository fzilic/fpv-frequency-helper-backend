package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.repository;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.DataConfiguration;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.ResultChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true,
    properties = {
        "logging.level.org.springframework=WARN",
        "logging.level.liquibase=WARN",
        "spring.liquibase.change-log=classpath:/liquibase.xml",
        "spring.datasource.url=jdbc:h2:mem:",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa",
        "spring.jpa.hibernate.ddl-auto=none"
    })
@ContextConfiguration(classes = DataConfiguration.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ResultRepositoryTest {

  @Autowired
  private ChannelRepository channelRepository;

  @Autowired
  private ResultRepository resultRepository;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private Result build(final Channel[] channels) {
    final Result result = Result.builder()
        .numberOfChannels(channels.length)
        .frequencies(frequencies(channels))
        .minimumSeparationChannel(0)
        .averageSeparationChannel(0.0)
        .minimumSeparationImd(0)
        .averageSeparationImd(0.0)
        .build();

    result.setChannels(Arrays.stream(channels).map(channel -> ResultChannel.builder()
        .result(result)
        .channel(channel)
        .minSeparationOtherChannels(0)
        .build()).collect(Collectors.toList()));

    return result;
  }

  @Test
  @Transactional
  void shouldCheckIfExists() {
    final List<Channel> preselected = channelRepository.findAllByBand_PreselectedIsTrue();

    final Channel[] first = {preselected.get(5), preselected.get(7), preselected.get(10)};
    final Channel[] second = {preselected.get(1), preselected.get(2), preselected.get(3)};
    final Channel[] third = {preselected.get(3), preselected.get(4)};

    resultRepository.saveAll(Arrays.asList(
        build(first),
        build(second),
        build(third)));

    entityManager.flush();

    assertThat(resultRepository.existsByFrequencies(frequencies(first))).isTrue();
    assertThat(resultRepository.existsByFrequencies(frequencies(preselected.get(1), preselected.get(7), preselected.get(10)))).isFalse();

    assertThat(resultRepository.existsByFrequencies(frequencies(third))).isTrue();

    assertThat(resultRepository.existsByFrequencies(frequencies(preselected.get(7), preselected.get(5)))).isFalse();

    assertThat(resultRepository.existsByFrequencies(frequencies(preselected.get(3), preselected.get(5)))).isFalse();
  }

  private String frequencies(final Channel... channels) {
    return Stream.of(channels)
        .map(Channel::getFrequency)
        .map(Objects::toString)
        .collect(Collectors.joining(","));
  }

  @Test
  @Transactional
  void shouldSave() {
    final List<Channel> preselected = channelRepository.findAllByBand_PreselectedIsTrue();

    final Channel[] channels = {preselected.get(5), preselected.get(7), preselected.get(10)};

    final Result saved = resultRepository.save(build(channels));

    entityManager.flush();

    final String value = jdbcTemplate.queryForObject("SELECT frequencies FROM result WHERE id = ?", new Object[]{saved.getId()}, String.class);

    assertThat(value).isEqualTo(Stream.of(frequencies(channels)).map(Objects::toString).collect(Collectors.joining(",")));
  }
}