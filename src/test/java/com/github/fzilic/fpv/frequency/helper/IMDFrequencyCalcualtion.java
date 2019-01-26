package com.github.fzilic.fpv.frequency.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IMDFrequencyCalcualtion {

  @Test
  void test() {
    //5760, 5800 and 5840
    //5760 and 5840

    final List<Integer> data = Arrays.asList(5760, 5800, 5840);

    final List<Integer> imds = new ArrayList<>();

    for (int i = 0; i < data.size(); i++) {
      final Integer f1 = data.get(i);
      for (int j = i; j < data.size(); j++) {
        final Integer f2 = data.get(j);

        imds.add((f1 * 2) - f2);
        imds.add((f2 * 2) - f1);
      }
    }

    assertThat(imds).isNotEmpty();
    assertThat(imds).contains(5760, 5840);

  }
}
