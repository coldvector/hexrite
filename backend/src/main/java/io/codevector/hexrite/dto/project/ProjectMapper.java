package io.codevector.hexrite.dto.project;

import io.codevector.hexrite.dto.chat.ChatMapper;
import io.codevector.hexrite.entity.project.Project;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Singleton;

@RegisterForReflection
@Singleton
public class ProjectMapper {

  private final ChatMapper chatMapper;

  public ProjectMapper(ChatMapper chatMapper) {
    this.chatMapper = chatMapper;
  }

  public ProjectResponse toProjectResponse(Project project) {
    return new ProjectResponse(project.id, project.title);
  }

  public ProjectChatResponse toProjectChatResponse(Project project) {
    return new ProjectChatResponse(
        project.id,
        project.title,
        project.chats.stream().map(chatMapper::toChatResponse).toList(),
        project.context,
        project.createdAt.toString(),
        project.updatedAt.toString());
  }
}
