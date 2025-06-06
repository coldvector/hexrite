package io.codevector.hexrite.service.chat;

import io.codevector.hexrite.dto.chat.ChatResponse;
import io.codevector.hexrite.entity.chat.Chat;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;

public interface ChatService {

  Uni<List<ChatResponse>> listChats(MultivaluedMap<String, String> filter);

  Uni<Chat> getChatById(String chatId);

  Uni<ChatResponse> createChat(String connectionId, String model);

  Uni<ChatResponse> updateChatTitle(String chatId, String name);

  Uni<ChatResponse> addToProject(String chatId, String projectId);

  Uni<ChatResponse> removeFromProject(String chatId);

  Multi<JsonObject> chat(String chatId, String message);

  Uni<Void> deleteChatById(String chatId);

  Uni<Void> deleteChats(MultivaluedMap<String, String> filter);
}
