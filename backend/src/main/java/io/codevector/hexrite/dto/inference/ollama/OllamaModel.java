package io.codevector.hexrite.dto.inference.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record OllamaModel(
    @JsonProperty("name") String name,
    @JsonProperty("model") String model,
    @JsonProperty("size") long size,
    @JsonProperty("digest") String digest,
    @JsonProperty("parentModel") String parentModel,
    @JsonProperty("format") String format,
    @JsonProperty("family") String family,
    @JsonProperty("parameterSize") String parameterSize,
    @JsonProperty("quantizationLevel") String quantizationLevel) {}
