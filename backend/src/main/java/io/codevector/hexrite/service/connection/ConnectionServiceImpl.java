package io.codevector.hexrite.service.connection;

import io.codevector.hexrite.dto.connection.ConnectionCreateRequest;
import io.codevector.hexrite.models.SimpleResponse;
import io.codevector.hexrite.persistence.Connection;
import io.codevector.hexrite.repository.ConnectionRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ConnectionServiceImpl implements ConnectionService {

  private static final Logger LOGGER = Logger.getLogger(ConnectionService.class.getSimpleName());

  private final ConnectionRepository connectionRepository;

  @Inject
  public ConnectionServiceImpl(ConnectionRepository connectionRepository) {
    this.connectionRepository = connectionRepository;
  }

  @WithSession
  @Override
  public Uni<Response> listConnections() {
    return connectionRepository
        .listAll(Sort.by("name").and("createdAt"))
        .onItem()
        .transform(list -> Response.ok(list).build())
        .onFailure()
        .invoke(failure -> LOGGER.error("Failed to list connections", failure))
        .onFailure()
        .transform(
            throwable ->
                new WebApplicationException(
                    Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(
                            SimpleResponse.create(Status.INTERNAL_SERVER_ERROR.getReasonPhrase()))
                        .build()));
  }

  @WithTransaction
  @Override
  public Uni<Response> createConnection(ConnectionCreateRequest request) {
    Connection connection = new Connection(request);
    return connectionRepository
        .persist(connection)
        .onItem()
        .transform(item -> Response.ok(item).build());
  }

  @WithTransaction
  @Override
  public Uni<Response> removeConnection(String connectionId) {
    return connectionRepository
        .delete(connectionId)
        .onItem()
        .transform(b -> Response.ok().build())
        .onFailure()
        .invoke(failure -> LOGGER.error("Failed to remove connection", failure))
        .onFailure()
        .transform(
            throwable ->
                new WebApplicationException(
                    Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(SimpleResponse.create("Failed to remove connection"))
                        .build()));
  }
}
