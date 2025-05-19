package io.codevector.hexrite.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ProjectResponse(@JsonProperty("id") String id, @JsonProperty("title") String title) {}
