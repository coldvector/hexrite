package io.codevector.hexrite.repository.chat;

import io.codevector.hexrite.dto.connection.ConnectionType;
import io.codevector.hexrite.entity.chat.Chat;
import io.codevector.hexrite.exceptions.ResourceNotFoundException;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChatRepository implements PanacheRepositoryBase<Chat, String> {

  public Uni<Chat> findByIdWithMessages(String chatId) {
    return find("SELECT c FROM Chat c LEFT JOIN FETCH c.messages WHERE c.id = ?1", chatId)
        .singleResult()
        .onFailure()
        .transform(t -> new ResourceNotFoundException("Chat not found"));
  }

  public PanacheQuery<Chat> findByFilters(
      String model, String connectionId, ConnectionType connectionType) {

    StringBuilder query = new StringBuilder("1=1");
    Parameters parameters = new Parameters();

    if (model != null && !model.isBlank()) {
      query.append(" AND model = :model");
      parameters.and("model", model);
    }

    if (connectionId != null && !connectionId.isBlank()) {
      query.append(" AND connection.id = :connectionId");
      parameters.and("connectionId", connectionId);
    }

    if (connectionType != null) {
      query.append(" AND connection.type = :connectionType");
      parameters.and("connectionType", connectionType);
    }

    return find(query.toString(), parameters);
  }

  public Uni<Long> deleteByFilters(
      String model, String connectionId, ConnectionType connectionType) {

    StringBuilder query = new StringBuilder("1=1");
    Parameters parameters = new Parameters();

    if (model != null && !model.isBlank()) {
      query.append(" AND model = :model");
      parameters.and("model", model);
    }

    if (connectionId != null && !connectionId.isBlank()) {
      query.append(" AND connection.id = :connectionId");
      parameters.and("connectionId", connectionId);
    }

    if (connectionType != null) {
      query.append(" AND connection.type = :connectionType");
      parameters.and("connectionType", connectionType);
    }

    return delete(query.toString(), parameters);
  }
}
