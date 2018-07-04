package com.github.fzilic.fpv.frequency.helper.backend.data.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fzilic.fpv.frequency.helper.backend.Validations.Common;
import com.github.fzilic.fpv.frequency.helper.backend.Validations.SelectionRequest;
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
  @JsonProperty("nickname")
  private String nickname;

  @Valid
  @NotNull(groups = {SelectionRequest.class})
  @Size(min = 1, groups = {SelectionRequest.class})
  @JsonProperty("availableChannels")
  private List<Channel> availableChannels;

  @JsonProperty("recommendedChannel")
  private Channel recommendedChannel;

  @JsonIgnore
  private Integer ordinal;

}
