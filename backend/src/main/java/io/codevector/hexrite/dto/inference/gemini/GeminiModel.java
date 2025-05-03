package io.codevector.hexrite.dto.inference.gemini;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record GeminiModel(
    @JsonProperty("name") String name,
    @JsonProperty("displayName") String displayName,
    @JsonProperty("description") String description,
    @JsonProperty("inputTokenLimit") long inputTokenLimit,
    @JsonProperty("outputTokenLimit") long outputTokenLimit,
    @JsonProperty("temperature") float temperature,
    @JsonProperty("topP") float topP,
    @JsonProperty("topK") float topK,
    @JsonProperty("maxTemperature") float maxTemperature) {}
