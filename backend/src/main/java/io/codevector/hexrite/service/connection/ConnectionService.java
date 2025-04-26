package io.codevector.hexrite.service.connection;

import io.codevector.hexrite.dto.connection.ConnectionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public interface ConnectionService {

  public Uni<Response> listConnections();

  public Uni<Response> getConnectionById(String connectionId);

  public Uni<Response> createConnection(ConnectionRequest request);

  public Uni<Response> updateConnection(String connectionId, ConnectionRequest request);

  public Uni<Response> removeConnection(String connectionId);
}
