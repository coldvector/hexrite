package io.codevector.hexrite.service.project;

import io.codevector.hexrite.dto.project.ProjectResponse;
import io.codevector.hexrite.entity.project.Project;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface ProjectService {

  Uni<List<ProjectResponse>> listProjects();

  Uni<Project> getProjectById(String projectId);

  Uni<ProjectResponse> createProject(String title, String context);

  Uni<ProjectResponse> updateProject(String projectId, String newTitle, String newContext);

  Uni<Void> deleteProjectById(String projectId);
}
