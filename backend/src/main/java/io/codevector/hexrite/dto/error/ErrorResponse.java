package io.codevector.hexrite.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ErrorResponse(
    @JsonProperty("error") String errorMessage) {

  public static ErrorResponse create(String message) {
    return new ErrorResponse(message);
  }
}
