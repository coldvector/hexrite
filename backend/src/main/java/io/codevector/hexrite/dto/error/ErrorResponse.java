package io.codevector.hexrite.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.core.Response.Status;

@RegisterForReflection
public record ErrorResponse(
    @JsonProperty("status") Status status, @JsonProperty("message") String message) {

  public static ErrorResponse create(Status status, String message) {
    return new ErrorResponse(status, message);
  }
}
