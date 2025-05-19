package io.codevector.hexrite.entity.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.codevector.hexrite.annotations.RequiredForJPA;
import io.codevector.hexrite.entity.chat.Chat;
import io.codevector.hexrite.entity.common.AbstractTimestampedEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project extends AbstractTimestampedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonProperty("id")
  public String id;

  @Column(name = "title", nullable = false)
  @JsonProperty("title")
  public String title;

  @OneToMany(
      mappedBy = "project",
      fetch = FetchType.LAZY,
      orphanRemoval = false,
      cascade = CascadeType.DETACH)
  @JsonProperty("chats")
  public List<Chat> chats = new ArrayList<>();

  @Column(name = "context", columnDefinition = "TEXT", nullable = true)
  @JsonProperty("context")
  public String context;

  @RequiredForJPA
  public Project() {}

  public Project(String title) {
    this.title = title;
  }

  public Project(String title, String context) {
    this.title = title;
    this.context = context;
  }

  public Project mutateProject(String newTitle, String newContext) {
    this.title = newTitle;
    this.context = newContext;
    return this;
  }
}
