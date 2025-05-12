package io.codevector.hexrite.dto.chat;

import io.codevector.hexrite.entity.chat.Chat;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Singleton;

@RegisterForReflection
@Singleton
public class ChatMapper {
  public ChatResponse toChatResponse(Chat chat) {
    return new ChatResponse(
      chat.id,
      chat.title,
      chat.connection.id,
      chat.model);
  }
}
