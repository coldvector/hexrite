package io.codevector.hexrite.dto.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.net.URI;

@RegisterForReflection
public record ConnectionRequest(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("type") ConnectionType type,
    @JsonProperty("baseUrl") URI baseUrl,
    @JsonProperty("apiKey") String apiKey,
    @JsonProperty("isEnabled") boolean isEnabled) {}
