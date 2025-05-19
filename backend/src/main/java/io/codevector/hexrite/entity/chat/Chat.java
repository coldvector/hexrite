package io.codevector.hexrite.entity.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.codevector.hexrite.annotations.RequiredForJPA;
import io.codevector.hexrite.entity.common.AbstractTimestampedEntity;
import io.codevector.hexrite.entity.connection.Connection;
import io.codevector.hexrite.entity.project.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat extends AbstractTimestampedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonProperty("id")
  public String id;

  @Column(name = "title", nullable = false)
  @JsonProperty("title")
  public String title;

  @OneToMany(
      mappedBy = "chatSession",
      fetch = FetchType.LAZY,
      orphanRemoval = true,
      cascade = CascadeType.ALL)
  public List<Message> messages = new ArrayList<>();

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(
      name = "connection_id",
      referencedColumnName = "id",
      nullable = true,
      foreignKey = @ForeignKey(name = "chat_to_connection_fk"))
  public Connection connection;

  @Column(name = "model", nullable = true)
  @JsonProperty("model")
  public String model;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(
      name = "project_id",
      referencedColumnName = "id",
      nullable = true,
      foreignKey = @ForeignKey(name = "chat_to_project_fk"))
  public Project project;

  @RequiredForJPA
  public Chat() {}

  public Chat(String title, Connection connection, String model) {
    this.title = title;
    this.connection = connection;
    this.model = model;
  }

  public Chat(Connection connection, String model) {
    this("", connection, model);
  }
}
