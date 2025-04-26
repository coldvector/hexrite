package io.codevector.hexrite.repository;

import io.codevector.hexrite.persistence.Connection;
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
}
