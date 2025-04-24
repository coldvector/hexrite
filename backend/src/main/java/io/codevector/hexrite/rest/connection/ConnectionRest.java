package io.codevector.hexrite.rest.connection;

import io.codevector.hexrite.dto.connection.ConnectionCreateRequest;
import io.codevector.hexrite.service.connection.ConnectionService;
import io.codevector.hexrite.service.connection.ConnectionServiceImpl;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> createConnection(ConnectionCreateRequest request) {
    return this.connectionService.createConnection(request);
  }

  @DELETE
  public Uni<Response> removeConnection(@QueryParam("id") String connectionId) {
    return this.connectionService.removeConnection(connectionId);
  }
}
