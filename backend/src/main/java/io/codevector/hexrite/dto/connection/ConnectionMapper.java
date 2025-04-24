package io.codevector.hexrite.dto.connection;

import io.codevector.hexrite.persistence.Connection;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Singleton;

@RegisterForReflection
@Singleton
public class ConnectionMapper {

  public ConnectionListResponse toConnectionListResponse(Connection connection) {
    return new ConnectionListResponse(
        connection.id,
        connection.name,
        connection.description,
        connection.type,
        connection.isEnabled);
  }
}
