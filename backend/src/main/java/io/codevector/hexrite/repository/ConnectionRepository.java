package io.codevector.hexrite.repository;

import io.codevector.hexrite.persistence.Connection;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ConnectionRepository implements PanacheRepositoryBase<Connection, String> {

  private static final Logger LOG = Logger.getLogger(ConnectionRepository.class.getSimpleName());
}
