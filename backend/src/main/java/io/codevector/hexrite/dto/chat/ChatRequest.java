package io.codevector.hexrite.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ChatRequest(
    @JsonProperty("connectionId") String connectionId, @JsonProperty("model") String model) {}
