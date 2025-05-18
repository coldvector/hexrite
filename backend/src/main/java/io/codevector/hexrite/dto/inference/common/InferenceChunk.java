package io.codevector.hexrite.dto.inference.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record InferenceChunk(@JsonProperty("text") String text) {}
