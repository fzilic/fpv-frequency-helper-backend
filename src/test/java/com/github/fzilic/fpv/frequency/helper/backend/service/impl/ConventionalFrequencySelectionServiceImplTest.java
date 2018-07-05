package com.github.fzilic.fpv.frequency.helper.backend.service.impl;


import com.github.fzilic.fpv.frequency.helper.backend.data.common.Pilot;
import com.github.fzilic.fpv.frequency.helper.backend.domain.Band;
import com.github.fzilic.fpv.frequency.helper.backend.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.exception.ConflictingPilotsException;
import com.github.fzilic.fpv.frequency.helper.backend.repository.BandRepository;
import com.github.fzilic.fpv.frequency.helper.backend.service.FrequencySelectionService;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
    "logging.level=ERROR",
    "logging.level.com.github.fzilic.fpv.frequency.helper.backend.service=TRACE",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.properties.hibernate.hbm2ddl.import_files_sql_extractor=org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor"
})
@DataJpaTest
public class ConventionalFrequencySelectionServiceImplTest {

  @Autowired
  private BandRepository repository;

  private FrequencySelectionService service;

  @Before
  public void initService() {
    service = new ConventionalFrequencySelectionServiceImpl();
  }


  private List<Channel> findSingleChannel(final List<Band> bands, final String bandName, final Integer channelNumber) {
    return bands.stream()
        .filter(band ->
            bandName.equals(band.getName()))
        .map(Band::getChannels)
        .flatMap(Collection::stream)
        .filter(channel ->
            channelNumber.equals(channel.getNumber()))
        .collect(Collectors.toList());
  }

  @Test(expected = ConflictingPilotsException.class)
  public void shouldFailWithSingleChannelPilotsConflicting() {
    final List<Band> bands = repository.findAll();

    final Pilot pilot1 = Pilot.builder()
        .nickname("first")
        .availableChannels(findSingleChannel(bands, "A", 2))
        .build();

    final Pilot pilot2 = Pilot.builder()
        .nickname("second")
        .availableChannels(findSingleChannel(bands, "B", 7))
        .build();

    service.recommendChannels(8, 8, Arrays.asList(pilot1, pilot2));

    fail("Should not happen");
  }

  @Test
  public void shouldRecommendSimple() {
    final List<Band> bands = repository.findAll();

    final Pilot pilot1 = Pilot.builder()
        .nickname("first")
        .availableChannels(bands.stream()
            .filter(band ->
                Arrays.asList("A", "B").contains(band.getName()))
            .map(Band::getChannels)
            .flatMap(Collection::stream)
            .collect(Collectors.toList()))
        .build();

    final Pilot pilot2 = Pilot.builder()
        .nickname("second")
        .availableChannels(bands.stream()
            .filter(band ->
                "F".equals(band.getName()))
            .map(Band::getChannels)
            .flatMap(Collection::stream)
            .filter(channel -> Arrays.asList(1, 2).contains(channel.getNumber()))
            .collect(Collectors.toList()))
        .build();

    final Pilot pilot3 = Pilot.builder()
        .nickname("second")
        .availableChannels(bands.stream()
            .filter(band ->
                "E".equals(band.getName()))
            .map(Band::getChannels)
            .flatMap(Collection::stream)
            .filter(channel -> Arrays.asList(1, 5).contains(channel.getNumber()))
            .collect(Collectors.toList()))
        .build();

    final List<List<Pilot>> recommendations = service.recommendChannels(20, 20, Arrays.asList(pilot1, pilot2, pilot3));

    assertThat(recommendations).hasSize(30);

    // assertThat(recommendations.get(0).getRecommendedChannel()).isNotNull();
    // assertThat(recommendations.get(1).getRecommendedChannel()).isNotNull();
    // assertThat(recommendations.get(3).getRecommendedChannel()).isNotNull();

    // recommendations.stream()
    //     .map(pilots ->
    //         pilots.stream()
    //             .map(pilot ->
    //                 pilot.getNickname() + " " +
    //                     pilot.getRecommendedChannel().getBand().getName() +
    //                     pilot.getRecommendedChannel().getNumber() +
    //                     " (" + pilot.getRecommendedChannel().getFrequency() + ")")
    //             .collect(Collectors.toList()))
    //     .collect(Collectors.toList())
    //     .forEach(strings ->
    //         log.warn("{}", strings));

  }


}