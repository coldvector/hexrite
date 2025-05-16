package io.codevector.hexrite.service.chat;

import io.codevector.hexrite.dto.chat.ChatMapper;
import io.codevector.hexrite.dto.chat.ChatResponse;
import io.codevector.hexrite.dto.connection.ConnectionType;
import io.codevector.hexrite.entity.chat.Chat;
import io.codevector.hexrite.entity.connection.Connection;
import io.codevector.hexrite.exceptions.ResourceNotFoundException;
import io.codevector.hexrite.repository.chat.ChatRepository;
import io.codevector.hexrite.service.connection.ConnectionService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.common.NotImplementedYet;

@ApplicationScoped
public class ChatServiceImpl implements ChatService {

  private static final Logger LOG = Logger.getLogger(ChatService.class.getSimpleName());

  private final ChatRepository chatRepository;
  private final ChatMapper chatMapper;
  private final ConnectionService connectionService;

  @Inject
  public ChatServiceImpl(
      ChatRepository chatRepository, ChatMapper chatMapper, ConnectionService connectionService) {
    this.chatRepository = chatRepository;
    this.chatMapper = chatMapper;
    this.connectionService = connectionService;
  }

  @WithSession
  @Override
  public Uni<List<ChatResponse>> listChats(MultivaluedMap<String, String> filter) {
    LOG.debugf("listChats");

    String model = filter.getFirst("model");
    String connectionId = filter.getFirst("connectionId");
    ConnectionType connectionType = ConnectionType.fromString(filter.getFirst("connectionType"));

    return chatRepository
        .findByFilters(model, connectionId, connectionType)
        .project(ChatResponse.class)
        .list();
  }

  @WithSession
  @Override
  public Uni<Chat> getChatById(String chatId) {
    LOG.debugf("getChatById: chatId=\"%s\"", chatId);

    return chatRepository.findById(chatId);
  }

  @WithTransaction
  @Override
  public Uni<ChatResponse> createChat(String connectionId, String model) {
    LOG.debugf("createChat: connectionId=\"%s\", model=\"%s\"", connectionId, model);

    return getConnectionById(connectionId)
        .onItem()
        .transform(conn -> new Chat(conn, model))
        .chain(chatRepository::persist)
        .map(chatMapper::toChatResponse);
  }

  @WithTransaction
  @Override
  public Uni<ChatResponse> updateChatTitle(String chatId, String newTitle) {
    LOG.debugf("updateChatTitle: chatId=\"%s\", name=\"%s\"", chatId, newTitle);

    return chatRepository
        .findById(chatId)
        .onItem()
        .invoke(chat -> chat.title = newTitle)
        .map(chatMapper::toChatResponse);
  }

  @WithTransaction
  @Override
  public Uni<Void> deleteChatById(String chatId) {
    LOG.debugf("deleteChatById: chatId=\"%s\"", chatId);

    return chatRepository
        .deleteById(chatId)
        .onItem()
        .transformToUni(
            deleted ->
                deleted
                    ? Uni.createFrom().voidItem()
                    : Uni.createFrom().failure(new ResourceNotFoundException("Chat not found")));
  }

  @WithTransaction
  @Override
  public Uni<Void> deleteChats(MultivaluedMap<String, String> filter) {
    LOG.debugf("deleteChats");

    String model = filter.getFirst("model");
    String connectionId = filter.getFirst("connectionId");
    ConnectionType connectionType = ConnectionType.fromString(filter.getFirst("connectionType"));

    return chatRepository
        .deleteByFilters(model, connectionId, connectionType)
        .onItem()
        .transformToUni(
            deleteCount ->
                deleteCount > 0
                    ? Uni.createFrom().voidItem()
                    : Uni.createFrom()
                        .failure(new ResourceNotFoundException("No such chats found")));
  }

  private Uni<Connection> getConnectionById(String connectionId) {
    return connectionService.getConnectionById(connectionId);
  }

}
