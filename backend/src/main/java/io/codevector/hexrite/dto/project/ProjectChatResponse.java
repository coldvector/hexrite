package io.codevector.hexrite.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.codevector.hexrite.dto.chat.ChatResponse;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;

@RegisterForReflection
public record ProjectChatResponse(
    @JsonProperty("id") String id,
    @JsonProperty("title") String title,
    @JsonProperty("chats") List<ChatResponse> chats,
    @JsonProperty("context") String context,
    @JsonProperty("createdAt") String createdAt,
    @JsonProperty("updatedAt") String updatedAt) {}
