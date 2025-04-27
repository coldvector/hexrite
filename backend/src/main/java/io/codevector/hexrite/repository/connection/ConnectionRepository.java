package io.codevector.hexrite.repository.connection;

import io.codevector.hexrite.entity.connection.Connection;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ConnectionRepository implements PanacheRepositoryBase<Connection, String> {

  private static final Logger LOG = Logger.getLogger(ConnectionRepository.class.getSimpleName());

  public Uni<Connection> findByName(String name) {
    LOG.debugf("findByName: name=\"%s\"", name);
    return find("name", name).firstResult();
  }

  public Uni<Connection> findByIdOrName(String id, String name) {
    LOG.debugf("findByIdOrName: id=\"%s\", name=\"%s\"", id, name);
    return find("id = ?1 or name = ?2", id, name).firstResult();
  }

  public Uni<Connection> findByIdNotName(String id, String name) {
    LOG.debugf("findByIdNotName: id=\"%s\", name=\"%s\"", id, name);
    return find("id = ?1 and name != ?2", id, name).firstResult();
  }

  public Uni<Connection> findByNameNotId(String id, String name) {
    LOG.infof("findByNameNotId: id=\"%s\", name=\"%s\"", id, name);
    return find("id != ?1 and name = ?2", id, name).firstResult();
  }
}
