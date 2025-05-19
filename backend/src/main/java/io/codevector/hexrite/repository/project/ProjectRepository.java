package io.codevector.hexrite.repository.project;

import io.codevector.hexrite.entity.project.Project;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProjectRepository implements PanacheRepositoryBase<Project, String> {}
