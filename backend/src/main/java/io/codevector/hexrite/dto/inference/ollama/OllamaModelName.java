package io.codevector.hexrite.dto.inference.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record OllamaModelName(
    @JsonProperty("connectionId") String connectionId,
    @JsonProperty("modelName") String modelName) {}
