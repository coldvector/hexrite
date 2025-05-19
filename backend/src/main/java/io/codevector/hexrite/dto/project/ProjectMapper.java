package io.codevector.hexrite.dto.project;

import io.codevector.hexrite.entity.project.Project;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Singleton;

@RegisterForReflection
@Singleton
public class ProjectMapper {

  public ProjectResponse toProjectResponse(Project project) {
    return new ProjectResponse(project.id, project.title);
  }
}
