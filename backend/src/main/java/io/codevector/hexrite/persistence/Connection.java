package io.codevector.hexrite.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.codevector.hexrite.models.connection.ConnectionType;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.net.URI;
import java.time.Instant;
import java.util.UUID;
import org.jboss.logging.Logger;

@Entity
@Table(name = "connection")
public class Connection extends PanacheEntityBase {

  private static final Logger LOGGER = Logger.getLogger(Connection.class.getSimpleName());

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonIgnore
  private String id;

  @Column(nullable = false, unique = true)
  @JsonProperty("name")
  private String name;

  @Column(name = "description")
  @JsonProperty("description")
  private String description;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  @JsonProperty("type")
  private ConnectionType type;

  @Column(name = "base_url")
  @JsonProperty("baseURL")
  private URI baseURL;

  @Column(name = "api_key")
  @JsonProperty("apiKey")
  private String apiKey;

  @Column(name = "created_at", nullable = false, updatable = false)
  @JsonProperty("createAt")
  private Instant createdAt;

  @Column(name = "updated_at")
  @JsonProperty("updatedAt")
  private Instant updatedAt;

  @Column(name = "enabled", nullable = false)
  @JsonProperty("isEnabled")
  private boolean isEnabled;

  @PrePersist
  public void prePersist() {
    this.createdAt = Instant.now();
    if (this.id == null) {
      LOGGER.debugf("Generating new UUID manually for LLMProvider");
      this.id = UUID.randomUUID().toString();
    } else {
      LOGGER.debugf("Using existing UUID for LLMProvider: \"%s\"", this.id);
    }
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = Instant.now();
  }
}
