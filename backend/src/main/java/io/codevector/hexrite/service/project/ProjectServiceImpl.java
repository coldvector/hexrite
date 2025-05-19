package io.codevector.hexrite.service.project;

import io.codevector.hexrite.dto.project.ProjectMapper;
import io.codevector.hexrite.dto.project.ProjectResponse;
import io.codevector.hexrite.entity.project.Project;
import io.codevector.hexrite.exceptions.ResourceNotFoundException;
import io.codevector.hexrite.repository.project.ProjectRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ProjectServiceImpl implements ProjectService {

  private static final Logger LOG = Logger.getLogger(ProjectService.class.getSimpleName());

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;

  @Inject
  public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
    this.projectRepository = projectRepository;
    this.projectMapper = projectMapper;
  }

  @WithSession
  @Override
  public Uni<List<ProjectResponse>> listProjects() {
    LOG.debugf("listProjects");

    return projectRepository
        .findAll()
        .list()
        .map(list -> list.stream().map(projectMapper::toProjectResponse).toList());
  }

  @WithSession
  @Override
  public Uni<Project> getProjectById(String projectId) {
    LOG.debugf("getProjectById: projectId=\"%s\"", projectId);

    return projectRepository
        .findById(projectId)
        .onItem()
        .ifNull()
        .failWith(() -> new ResourceNotFoundException("Project not found"));
  }

  @WithTransaction
  @Override
  public Uni<ProjectResponse> createProject(String title, String context) {
    LOG.debugf("createProject: title=\"%s\", context=\"%s\"", title, context);

    if (title == null || title.isBlank()) {
      return Uni.createFrom().failure(new IllegalArgumentException("Title cannot be empty"));
    }

    return projectRepository
        .persist(new Project(title, context))
        .map(projectMapper::toProjectResponse);
  }

  @WithTransaction
  @Override
  public Uni<ProjectResponse> updateProject(String projectId, String newTitle, String newContext) {
    LOG.debugf(
        "updateProject: projectId=\"%s\", newTitle=\"%s\", newContext=\"%s\"",
        projectId, newTitle, newContext);

    if (newTitle == null || newTitle.isBlank()) {
      return Uni.createFrom().failure(new IllegalArgumentException("Title cannot be empty"));
    }

    return projectRepository
        .findById(projectId)
        .onItem()
        .ifNull()
        .failWith(() -> new ResourceNotFoundException("Project not found"))
        .onItem()
        .transform(existingProject -> existingProject.mutateProject(newTitle, newContext))
        .map(projectMapper::toProjectResponse);
  }

  @Override
  public Uni<Void> deleteProjectById(String projectId) {
    LOG.debugf("deleteProject: projectId=\"%s\"", projectId);

    return projectRepository
        .deleteById(projectId)
        .onItem()
        .transformToUni(
            deleted ->
                deleted
                    ? Uni.createFrom().voidItem()
                    : Uni.createFrom().failure(new ResourceNotFoundException("Project not found")));
  }
}
