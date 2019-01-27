package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.Basic;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Result;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResult {

  @JsonView(Basic.class)
  private Result result;

  @JsonView(Basic.class)
  private List<Pilot> pilots;

}
