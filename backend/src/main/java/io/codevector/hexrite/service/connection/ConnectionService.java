package io.codevector.hexrite.service.connection;

import io.codevector.hexrite.dto.connection.ConnectionCreateRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public interface ConnectionService {

  public Uni<Response> listConnections();

  public Uni<Response> createConnection(ConnectionCreateRequest request);

  public Uni<Response> removeConnection(String connectionId);
}
