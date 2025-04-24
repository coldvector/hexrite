package io.codevector.hexrite.rest.connection;

import io.codevector.hexrite.service.connection.ConnectionService;
import io.codevector.hexrite.service.connection.ConnectionServiceImpl;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/connection")
public class ConnectionRest {

  private final ConnectionService connectionService;

  @Inject
  public ConnectionRest(ConnectionServiceImpl connectionService) {
    this.connectionService = connectionService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> listConnections() {
    return this.connectionService.listConnections();
  }
}
