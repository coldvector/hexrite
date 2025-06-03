package io.codevector.hexrite.service.chat;

import io.codevector.hexrite.dto.chat.ChatMapper;
import io.codevector.hexrite.dto.chat.ChatResponse;
import io.codevector.hexrite.dto.chat.ChatRole;
import io.codevector.hexrite.dto.connection.ConnectionType;
import io.codevector.hexrite.dto.error.ErrorResponse;
import io.codevector.hexrite.entity.chat.Chat;
import io.codevector.hexrite.entity.chat.Message;
import io.codevector.hexrite.entity.connection.Connection;
import io.codevector.hexrite.exceptions.ResourceNotFoundException;
import io.codevector.hexrite.repository.chat.ChatRepository;
import io.codevector.hexrite.repository.chat.MessageRepository;
import io.codevector.hexrite.service.connection.ConnectionService;
import io.codevector.hexrite.service.inference.common.InferenceService;
import io.codevector.hexrite.service.inference.common.InferenceServiceRegistry;
import io.codevector.hexrite.service.project.ProjectService;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ChatServiceImpl implements ChatService {

  private static final Logger LOG = Logger.getLogger(ChatService.class.getSimpleName());

  private final ChatRepository chatRepository;
  private final MessageRepository messageRepository;
  private final ChatMapper chatMapper;
  private final ConnectionService connectionService;
  private final ProjectService projectService;
  private final InferenceServiceRegistry inferenceRegistry;

  @Inject
  public ChatServiceImpl(
      ChatRepository chatRepository,
      MessageRepository messageRepository,
      ChatMapper chatMapper,
      ConnectionService connectionService,
      ProjectService projectService,
      InferenceServiceRegistry inferenceRegistry) {
    this.chatRepository = chatRepository;
    this.messageRepository = messageRepository;
    this.chatMapper = chatMapper;
    this.connectionService = connectionService;
    this.projectService = projectService;
    this.inferenceRegistry = inferenceRegistry;
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
        .list()
        .map(list -> list.stream().map(chatMapper::toChatResponse).toList());
  }

  @WithSession
  @Override
  public Uni<Chat> getChatById(String chatId) {
    LOG.debugf("getChatById: chatId=\"%s\"", chatId);

    return chatRepository
        .findByIdWithMessages(chatId)
        .onFailure()
        .transform(t -> new ResourceNotFoundException("Chat not found"));
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

    if (newTitle == null || newTitle.isBlank()) {
      return Uni.createFrom().failure(new IllegalArgumentException("Title cannot be empty"));
    }

    return getChatById(chatId)
        .onItem()
        .invoke(chat -> chat.title = newTitle)
        .map(chatMapper::toChatResponse);
  }

  @WithTransaction
  @Override
  public Uni<ChatResponse> addToProject(String chatId, String projectId) {
    LOG.debugf("addToProject: chatId=\"%s\", projectId=\"%s\"", chatId, projectId);

    return getChatById(chatId)
        .call(
            chat ->
                projectService
                    .getProjectById(projectId)
                    .onItem()
                    .invoke(project -> chat.project = project))
        .map(chatMapper::toChatResponse);
  }

  @WithTransaction
  @Override
  public Uni<ChatResponse> removeFromProject(String chatId) {
    LOG.debugf("removeFromProject: chatId=\"%s\"", chatId);

    return getChatById(chatId)
        .call(chat -> Uni.createFrom().item(chat).invoke(c -> c.project = null))
        .map(chatMapper::toChatResponse);
  }

  @Override
  public Multi<JsonObject> chat(String chatId, String message) {
    LOG.debugf("chat: chatId=\"%s\", message=\"%s\"", chatId, message);

    return persistUserPrompt(chatId, message)
        .onItem()
        .transformToMulti(chat -> streamAndBufferInference(chat));
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

  private Uni<Chat> persistUserPrompt(String chatId, String message) {
    return Panache.withTransaction(
        () ->
            getChatById(chatId)
                .call(
                    chat ->
                        messageRepository
                            .persist(new Message(chat, ChatRole.USER, message))
                            .onItem()
                            .invoke(m -> chat.messages.add(m))));
  }

  private Multi<JsonObject> streamAndBufferInference(Chat chat) {
    InferenceService service =
        inferenceRegistry
            .getInferenceService(chat.connection.type)
            .orElseThrow(
                () ->
                    new IllegalArgumentException("Unsupported LLM type: " + chat.connection.type));

    AtomicReference<StringBuilder> buffer = new AtomicReference<>(new StringBuilder());

    return service
        .generateChat(chat.connection.id, chat.model, chat.messages)
        .onItem()
        .invoke(chunk -> bufferContent(chunk, buffer))
        .onCompletion()
        .call(() -> persistInference(chat, buffer.get().toString()))
        .onFailure()
        .recoverWithMulti(
            e -> Multi.createFrom().item(ErrorResponse.create(e.getMessage()).asJsonObject()));
  }

  private void bufferContent(JsonObject chunk, AtomicReference<StringBuilder> buffer) {
    String text = chunk.getString("text");

    if (text != null) {
      buffer.get().append(text);
    }
  }

  private Uni<Message> persistInference(Chat chat, String response) {
    LOG.debugf("persistAssistantMessage: chatId=\"%s\", response=\"%s\"", chat.id, response);

    return Panache.withTransaction(
        () ->
            messageRepository
                .persist(new Message(chat, ChatRole.MODEL, response))
                .onItem()
                .invoke(m -> chat.messages.add(m)));
  }

  private Uni<Connection> getConnectionById(String connectionId) {
    return connectionService.getConnectionById(connectionId);
  }
}
