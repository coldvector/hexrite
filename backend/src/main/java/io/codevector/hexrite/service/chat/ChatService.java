package io.codevector.hexrite.service.chat;

import io.codevector.hexrite.dto.connection.ConnectionType;
import io.codevector.hexrite.entity.chat.Chat;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface ChatService {

  Uni<List<Chat>> listChats();

  Uni<List<Chat>> listChatsByConnectionId(String connectionId);

  Uni<List<Chat>> listChatsByConnectionType(ConnectionType connectionType);

  Uni<Chat> getChatById(String chatId);

  // Uni<Void> createChat();

  Uni<Chat> updateChatTitle(String chatId, String name);

  // Uni<Void> addMessage(String chatId, String message);

  Uni<Void> deleteChatById(String chatId);
}
