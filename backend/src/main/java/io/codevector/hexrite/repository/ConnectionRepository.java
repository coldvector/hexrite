package io.codevector.hexrite.repository;

import io.codevector.hexrite.persistence.Connection;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ConnectionRepository implements PanacheRepository<Connection> {

  private static final Logger LOGGER = Logger.getLogger(ConnectionRepository.class.getSimpleName());

  public Uni<Boolean> delete(String connectionId) {
    return delete("id", connectionId)
        .onItem()
        .invoke(l -> LOGGER.infof("Deleted connection: %d", l))
        .onItem()
        .transform(l -> l > 0);
  }
}
