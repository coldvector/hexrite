package io.codevector.hexrite.service.chat;

import io.codevector.hexrite.dto.connection.ConnectionType;
import io.codevector.hexrite.entity.chat.Chat;
import io.codevector.hexrite.repository.chat.ChatRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.common.NotImplementedYet;

@ApplicationScoped
public class ChatServiceImpl implements ChatService {

  private static final Logger LOG = Logger.getLogger(ChatService.class.getSimpleName());

  private final ChatRepository chatRepository;

  @Inject
  public ChatServiceImpl(ChatRepository chatRepository) {
    this.chatRepository = chatRepository;
  }

  @Override
  @WithSession
  public Uni<List<Chat>> listChats() {
    LOG.debugf("listChats");

    return chatRepository.listAll();
  }

  @Override
  public Uni<List<Chat>> listChatsByConnectionId(String connectionId) {
    throw new NotImplementedYet();
  }

  @Override
  public Uni<List<Chat>> listChatsByConnectionType(ConnectionType connectionType) {
    throw new NotImplementedYet();
  }

  @Override
  public Uni<Chat> getChatById(String chatId) {
    throw new NotImplementedYet();
  }

  @Override
  public Uni<Chat> updateChatTitle(String chatId, String name) {
    throw new NotImplementedYet();
  }

  @Override
  public Uni<Void> deleteChatById(String chatId) {
    throw new NotImplementedYet();
  }
}
