package io.codevector.hexrite.service.connection;

import io.codevector.hexrite.dto.connection.ConnectionMapper;
import io.codevector.hexrite.dto.connection.ConnectionRequest;
import io.codevector.hexrite.models.SimpleResponse;
import io.codevector.hexrite.persistence.Connection;
import io.codevector.hexrite.repository.ConnectionRepository;
import io.codevector.hexrite.utils.JSONMapper;
import io.codevector.hexrite.utils.UniUtils;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ConnectionServiceImpl implements ConnectionService {

  private static final Logger LOG = Logger.getLogger(ConnectionService.class.getSimpleName());

  private final ConnectionRepository connectionRepository;
  private final ConnectionMapper connectionMapper;

  @Inject
  public ConnectionServiceImpl(
      ConnectionRepository connectionRepository, ConnectionMapper connectionMapper) {
    this.connectionRepository = connectionRepository;
    this.connectionMapper = connectionMapper;
  }

  @WithSession
  @Override
  public Uni<Response> listConnections() {
    LOG.debugf("listConnections");

    return connectionRepository
        .listAll(Sort.by("name").and("createdAt"))
        .map(list1 -> list1.stream().map(connectionMapper::toConnectionListResponse).toList())
        .onItem()
        .transform(list2 -> UniUtils.handleSuccess(list2));
  }

  @WithSession
  @Override
  public Uni<Response> getConnectionById(String connectionId) {
    LOG.debugf("getConnectionById: connectionId=\"%s\"", connectionId);

    return connectionRepository
        .findById(connectionId)
        .onItem()
        .ifNotNull()
        .invoke(connection -> LOG.debugf("Found connection: %s", connection))
        .onItem()
        .ifNull()
        .failWith(
            () ->
                UniUtils.handleFailure(
                    LOG,
                    Status.NOT_FOUND,
                    String.format("Connection with id '%s' not found", connectionId)))
        .onItem()
        .transform(UniUtils::handleSuccess);
  }

  @WithTransaction
  @Override
  public Uni<Response> createConnection(ConnectionRequest request) {
    LOG.debugf("createConnection: request=\"%s\"", JSONMapper.serialize(request));

    return validateCreateConnectionRequest(request)
        .chain(connectionRepository::persist)
        .map(connectionMapper::toConnectionListResponse)
        .onItem()
        .transform(UniUtils::handleSuccess);
  }

  @WithTransaction
  @Override
  public Uni<Response> removeConnection(String connectionId) {
    LOG.debugf("removeConnection: connectionId=\"%s\"", connectionId);

    return connectionRepository
        .findById(connectionId)
        .onItem()
        .ifNotNull()
        .invoke(connection -> LOG.debugf("Found connection: %s", connection))
        .chain(connection -> connectionRepository.deleteById(connectionId))
        .onItem()
        .transform(
            b -> {
              if (b) {
                return Response.ok().build();
              } else {
                return Response.status(Status.NOT_FOUND)
                    .entity(SimpleResponse.create("Connection not found"))
                    .build();
              }
            });
  }

  private Uni<Connection> validateCreateConnectionRequest(ConnectionRequest request) {
    return connectionRepository
        .findByName(request.name())
        .onItem()
        .ifNotNull()
        .failWith(
            () ->
                UniUtils.handleFailure(
                    LOG,
                    Status.CONFLICT,
                    String.format("Connection '%s' already exists", request.name())))
        .onItem()
        .ifNull()
        .continueWith(new Connection(request));
  }
}
