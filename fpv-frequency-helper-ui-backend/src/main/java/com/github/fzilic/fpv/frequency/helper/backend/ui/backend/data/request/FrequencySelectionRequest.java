package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.request;

import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Validations.Common;
import com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.common.Pilot;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class FrequencySelectionRequest {

  @Valid
  @NotNull(groups = {Common.class})
  @Size(min = 1, groups = {Common.class})
  private List<Pilot> pilots;

  @NotNull(groups = {Common.class})
  @Min(value = 0, groups = {Common.class})
  @Max(value = 100, groups = {Common.class})
  private Integer lowerSpacing;

  @NotNull(groups = {Common.class})
  @Min(value = 0, groups = {Common.class})
  @Max(value = 100, groups = {Common.class})
  private Integer upperSpacing;


}
