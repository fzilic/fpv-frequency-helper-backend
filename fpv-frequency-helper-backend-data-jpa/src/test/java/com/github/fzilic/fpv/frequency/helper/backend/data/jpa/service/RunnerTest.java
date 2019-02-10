package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service;


import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.service.impl.DefaultGeneratorService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class RunnerTest {

  static Stream<Arguments> params() {
    return Stream.of(
        Arguments.arguments(asList(m(1), m(2), m(3), m(4), m(5)),
            3, new ArrayList<>(asList(
                asList(m(1), m(2), m(3)),
                asList(m(1), m(2), m(4)),
                asList(m(1), m(2), m(5)),
                asList(m(1), m(3), m(4)),
                asList(m(1), m(3), m(5)),
                asList(m(1), m(4), m(5)),

                asList(m(2), m(3), m(4)),
                asList(m(2), m(3), m(5)),
                asList(m(2), m(4), m(5)),

                asList(m(3), m(4), m(5)))))
    );
  }

  private static Channel m(final Integer frequency) {
    final Channel channel = new Channel();
    channel.setFrequency(frequency);
    return channel;
  }

  @ParameterizedTest
  @MethodSource("params")
  void shouldFindAllCombinations(final List<Channel> all, final Integer count, final List<List<Channel>> expected) {
    DefaultGeneratorService.combinations(all, count, channels -> {
      expected.removeIf(data -> channels.stream().allMatch(found -> data.stream().anyMatch(ch -> ch.getFrequency().equals(found.getFrequency()))));
    });

    assertThat(expected).isEmpty();
  }


}