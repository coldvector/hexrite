package io.codevector.hexrite.dto.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.codevector.hexrite.models.connection.ConnectionType;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ConnectionResponse(
    @JsonProperty("id") String id,
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("type") ConnectionType type,
    @JsonProperty("isEnabled") boolean isEnabled) {}
