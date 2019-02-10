package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Validations.Common;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Validations.SelectionRequest;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.Basic;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.domain.Channel;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pilot {

  @NotNull(groups = {Common.class})
  @JsonView(Basic.class)
  private String nickname;

  @Valid
  @NotNull(groups = {SelectionRequest.class})
  @Size(min = 1, groups = {SelectionRequest.class})
  @JsonView(Basic.class)
  private List<Channel> availableChannels;

  @JsonView(Basic.class)
  private Channel recommendedChannel;

  @JsonView(Basic.class)
  private Integer ordinal;

  @JsonView(Basic.class)
  private Boolean preferMaximumSeparationFromOthers;

  @JsonView(Basic.class)
  private Integer minimumSeparationFromOthers;

}
