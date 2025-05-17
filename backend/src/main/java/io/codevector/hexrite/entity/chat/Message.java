package io.codevector.hexrite.entity.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.codevector.hexrite.annotations.RequiredForJPA;
import io.codevector.hexrite.dto.chat.ChatRole;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "messages")
public class Message extends PanacheEntityBase {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonProperty("id")
  public String id;

  @ManyToOne
  @JoinColumn(
      name = "chat_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "message_to_chat_fk"))
  @JsonIgnore
  public Chat chatSession;

  @Column(name = "role", nullable = false)
  @Convert(converter = ChatRoleConverter.class)
  @JsonProperty("role")
  public ChatRole role;

  @Column(name = "content", columnDefinition = "TEXT", nullable = false)
  public String content;

  @Column(name = "timestamp")
  @JsonProperty("timestamp")
  public Instant timestamp;

  @RequiredForJPA
  public Message() {}

  public Message(Chat chatSession, ChatRole role, String content) {
    this.chatSession = chatSession;
    this.role = role;
    this.content = content;
    this.timestamp = Instant.now();
  }
}
