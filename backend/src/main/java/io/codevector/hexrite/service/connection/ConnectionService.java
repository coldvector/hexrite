package io.codevector.hexrite.service.connection;

import io.codevector.hexrite.dto.connection.ConnectionRequest;
import io.codevector.hexrite.dto.connection.ConnectionResponse;
import io.codevector.hexrite.entity.connection.Connection;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface ConnectionService {

  public Uni<List<ConnectionResponse>> listConnections();

  public Uni<Connection> getConnectionById(String connectionId);

  public Uni<ConnectionResponse> createConnection(ConnectionRequest request);

  public Uni<ConnectionResponse> updateConnection(String connectionId, ConnectionRequest request);

  public Uni<Void> removeConnection(String connectionId);
}
