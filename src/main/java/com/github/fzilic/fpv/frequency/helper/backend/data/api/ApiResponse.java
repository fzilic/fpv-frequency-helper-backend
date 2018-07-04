package com.github.fzilic.fpv.frequency.helper.backend.data.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.Views.Basic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

  @JsonProperty("status")
  @JsonView({Basic.class})
  private ApiResponseStatus status;

  @JsonProperty("data")
  @JsonView({Basic.class})
  private T data;

  public static <T> ApiResponse<T> success() {
    return new ApiResponse<>(ApiResponseStatus.SUCCESS, null);
  }

  public static <T> ApiResponse<T> success(final T data) {
    return new ApiResponse<>(ApiResponseStatus.SUCCESS, data);
  }

}
