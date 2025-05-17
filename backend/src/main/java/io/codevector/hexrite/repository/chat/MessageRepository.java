package io.codevector.hexrite.repository.chat;

import io.codevector.hexrite.entity.chat.Message;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageRepository implements PanacheRepositoryBase<Message, String> {}
