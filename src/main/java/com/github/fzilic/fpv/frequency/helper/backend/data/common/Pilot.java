package com.github.fzilic.fpv.frequency.helper.backend.data.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.Validations.Common;
import com.github.fzilic.fpv.frequency.helper.backend.Validations.SelectionRequest;
import com.github.fzilic.fpv.frequency.helper.backend.Views.Basic;
import com.github.fzilic.fpv.frequency.helper.backend.domain.Channel;
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

  @JsonIgnore
  @JsonView(Basic.class)
  private Integer ordinal;

}
