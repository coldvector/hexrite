package io.codevector.hexrite.repository.chat;

import io.codevector.hexrite.entity.chat.Chat;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChatRepository implements PanacheRepositoryBase<Chat, String> {}
