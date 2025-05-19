package io.codevector.hexrite.service.connection;

import io.codevector.hexrite.dto.connection.ConnectionMapper;
import io.codevector.hexrite.dto.connection.ConnectionRequest;
import io.codevector.hexrite.dto.connection.ConnectionResponse;
import io.codevector.hexrite.entity.connection.Connection;
import io.codevector.hexrite.exceptions.ConflictException;
import io.codevector.hexrite.exceptions.ResourceNotFoundException;
import io.codevector.hexrite.repository.connection.ConnectionRepository;
import io.codevector.hexrite.utils.JSONMapper;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
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
  public Uni<List<ConnectionResponse>> listConnections() {
    LOG.debugf("listConnections");

    return connectionRepository
        .listAll(Sort.by("name").and("createdAt"))
        .map(list -> list.stream().map(connectionMapper::toConnectionResponse).toList());
  }

  @WithSession
  @Override
  public Uni<Connection> getConnectionById(String connectionId) {
    LOG.debugf("getConnectionById: connectionId=\"%s\"", connectionId);

    return connectionRepository
        .findById(connectionId)
        .onItem()
        .ifNull()
        .failWith(() -> new ResourceNotFoundException("Connection not found"));
  }

  @WithTransaction
  @Override
  public Uni<ConnectionResponse> createConnection(ConnectionRequest request) {
    LOG.debugf("createConnection: request=\"%s\"", JSONMapper.serialize(request));

    return validateCreateConnectionRequest(request)
        .chain(connectionRepository::persist)
        .map(connectionMapper::toConnectionResponse);
  }

  @WithTransaction
  @Override
  public Uni<ConnectionResponse> updateConnection(String connectionId, ConnectionRequest request) {
    LOG.debugf("updateConnection: request=\"%s\"", JSONMapper.serialize(request));

    return validateUpdateConnectionRequest(connectionId, request)
        .onItem()
        .transform(existingConnection -> existingConnection.mutateConnection(request))
        .map(connectionMapper::toConnectionResponse);
  }

  @WithTransaction
  @Override
  public Uni<Void> removeConnection(String connectionId) {
    LOG.debugf("removeConnection: connectionId=\"%s\"", connectionId);

    return connectionRepository
        .deleteById(connectionId)
        .onItem()
        .transformToUni(
            deleted ->
                deleted
                    ? Uni.createFrom().voidItem()
                    : Uni.createFrom()
                        .failure(new ResourceNotFoundException("Connection not found")));
  }

  private Uni<Connection> validateCreateConnectionRequest(ConnectionRequest request) {
    return connectionRepository
        .findByName(request.name())
        .onItem()
        .ifNotNull()
        .failWith(
            () ->
                new ConflictException(
                    String.format("Connection '%s' already exists", request.name())))
        .onItem()
        .ifNull()
        .continueWith(new Connection(request));
  }

  private Uni<Connection> validateUpdateConnectionRequest(
      String connectionId, ConnectionRequest request) {
    return connectionRepository
        .findById(connectionId)
        .onItem()
        .ifNull()
        .failWith(() -> new ResourceNotFoundException("Connection not found"))
        .chain(
            existingConnection ->
                validateConnectionNameChange(connectionId, request, existingConnection));
  }

  private Uni<Connection> validateConnectionNameChange(
      String connectionId, ConnectionRequest request, Connection existingConnection) {
    if (existingConnection.name.equals(request.name())) {
      return Uni.createFrom().item(existingConnection);
    } else {
      return connectionRepository
          .findByNameNotId(connectionId, request.name())
          .onItem()
          .ifNotNull()
          .failWith(
              () ->
                  new ConflictException(
                      String.format("Connection '%s' already exists", request.name())))
          .replaceWith(existingConnection);
    }
  }
}
