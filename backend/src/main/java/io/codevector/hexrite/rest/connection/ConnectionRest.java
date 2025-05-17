package io.codevector.hexrite.rest.connection;

import io.codevector.hexrite.dto.connection.ConnectionRequest;
import io.codevector.hexrite.rest.common.ResponseUtils;
import io.codevector.hexrite.service.connection.ConnectionService;
import io.codevector.hexrite.service.connection.ConnectionServiceImpl;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    return this.connectionService
        .listConnections()
        .onItem()
        .transform(list -> ResponseUtils.handleSuccess(list));
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> getConnectionById(@PathParam("id") String connectionId) {
    return this.connectionService
        .getConnectionById(connectionId)
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> createConnection(ConnectionRequest request) {
    return this.connectionService
        .createConnection(request)
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> updateConnection(
      @PathParam("id") String connectionId, ConnectionRequest request) {
    return this.connectionService
        .updateConnection(connectionId, request)
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> removeConnection(@PathParam("id") String connectionId) {
    return this.connectionService
        .removeConnection(connectionId)
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }
}
