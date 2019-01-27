package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.fzilic.fpv.frequency.helper.backend.data.jpa.Views.Basic;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class ApiResponse<T> {

  @JsonProperty("status")
  @JsonView({Basic.class})
  private ApiResponseStatus status;

  @JsonProperty("data")
  @JsonView({Basic.class})
  private T data;

  @JsonProperty("message")
  @JsonView({Basic.class})
  private String message;

  @JsonProperty("exceptions")
  @JsonView({Basic.class})
  private Collection<? extends Throwable> exceptions;

  public static <T> ApiResponse<T> error(final ApiResponseStatus status) {
    return error(status, null);
  }

  public static <T> ApiResponse<T> error(final ApiResponseStatus status, final String message) {
    return new ApiResponse<>(status, null, message, null);
  }

  public static <T> ApiResponse<T> success() {
    return success(null);
  }

  public static <T> ApiResponse<T> success(final T data) {
    return new ApiResponse<>(ApiResponseStatus.SUCCESS, data, null, null);
  }

}
