package io.codevector.hexrite.repository;

import io.codevector.hexrite.persistence.Connection;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConnectionRepository implements PanacheRepository<Connection> {

  public Uni<Connection> findByName(String name) {
    return find("name", name).firstResult();
  }

  public Uni<Boolean> exists(String name) {
    return count("name", name).onItem().transform(count -> count > 0);
  }
}
