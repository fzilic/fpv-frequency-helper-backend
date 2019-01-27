package com.github.fzilic.fpv.frequency.helper.backend.ui.backend.data.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import java.util.Optional;

public enum ApiResponseStatus {
  SUCCESS(0),
  TOO_MANY_COMBINATIONS(1000),
  UNKNOWN_ERROR(-9999);

  private final Integer code;

  @JsonCreator
  public static ApiResponseStatus forCode(final Integer code) {
    return Optional.ofNullable(code)
        .map(i -> Arrays.stream(ApiResponseStatus.values())
            .filter(status ->
                status.code.equals(i))
            .findFirst()
            .orElse(null))
        .orElse(null);
  }

  ApiResponseStatus(final Integer code) {
    this.code = code;
  }

  @JsonValue
  public Integer getCode() {
    return code;
  }

}
