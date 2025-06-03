package io.codevector.hexrite.repository.project;

import io.codevector.hexrite.entity.project.Project;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProjectRepository implements PanacheRepositoryBase<Project, String> {

  public Uni<Project> findByIdWithChats(String projectId) {
    return find("SELECT p FROM Project p LEFT JOIN FETCH p.chats WHERE p.id = ?1", projectId)
        .singleResult();
  }
}
