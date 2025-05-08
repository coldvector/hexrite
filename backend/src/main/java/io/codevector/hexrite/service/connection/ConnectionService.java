package io.codevector.hexrite.service.connection;

import io.codevector.hexrite.dto.connection.ConnectionRequest;
import io.codevector.hexrite.dto.connection.ConnectionResponse;
import io.codevector.hexrite.entity.connection.Connection;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface ConnectionService {

  Uni<List<ConnectionResponse>> listConnections();

  Uni<Connection> getConnectionById(String connectionId);

  Uni<ConnectionResponse> createConnection(ConnectionRequest request);

  Uni<ConnectionResponse> updateConnection(String connectionId, ConnectionRequest request);

  Uni<Void> removeConnection(String connectionId);
}
