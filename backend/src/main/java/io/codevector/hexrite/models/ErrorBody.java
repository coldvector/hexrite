package io.codevector.hexrite.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.core.Response.Status;

@RegisterForReflection
public record ErrorBody(
    @JsonProperty("status") Status status, @JsonProperty("message") String message) {

  public static ErrorBody create(Status status, String message) {
    return new ErrorBody(status, message);
  }
}
