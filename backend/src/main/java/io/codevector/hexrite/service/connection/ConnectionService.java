package io.codevector.hexrite.service.connection;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public interface ConnectionService {

  public Uni<Response> listConnections();
}
