package com.github.fzilic.fpv.frequency.helper.backend.data.jpa.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualityResult {

  private Integer minimumSeparationChannel;

  private Double averageSeparationChannel;

  private Integer minimumSeparationImd;

  private Double averageSeparationImd;

}
