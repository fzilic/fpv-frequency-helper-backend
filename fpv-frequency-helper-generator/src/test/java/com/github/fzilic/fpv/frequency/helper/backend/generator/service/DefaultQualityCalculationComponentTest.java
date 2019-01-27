package com.github.fzilic.fpv.frequency.helper.backend.generator.service;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import com.github.fzilic.fpv.frequency.helper.backend.generator.data.QualityResult;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.util.concurrent.ListenableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DefaultQualityCalculationComponentTest {

  @Test
  void shouldGenerate() {
    final ListenableFuture<QualityResult> calculate = new DefaultQualityCalculationComponent().calculate(Arrays.asList(c(5645), c(5771)));
    assertThat(calculate).isNotNull();
  }

  private Channel c(final int frequency) {
    return Channel.builder().frequency(frequency).build();
  }

  @Test
  void shouldGenerateBad() {
    final ListenableFuture<QualityResult> calculate = new DefaultQualityCalculationComponent().calculate(Arrays.asList(c(5760), c(5800), c(5840)));
    assertThat(calculate).isNotNull();
  }

}