package io.codevector.hexrite.entity.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.codevector.hexrite.annotations.RequiredForJPA;
import io.codevector.hexrite.dto.connection.ConnectionRequest;
import io.codevector.hexrite.dto.connection.ConnectionType;
import io.codevector.hexrite.entity.common.UriConverter;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import org.jboss.logging.Logger;

@Entity
@Table(name = "connections")
public class Connection extends PanacheEntityBase {

  private static final Logger LOG = Logger.getLogger(Connection.class.getSimpleName());

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonProperty("id")
  public String id;

  @Column(name = "name", nullable = false, unique = true)
  @JsonProperty("name")
  public String name;

  @Column(name = "description")
  @JsonProperty("description")
  public String description;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  @JsonProperty("type")
  public ConnectionType type;

  @Column(name = "base_url", nullable = false)
  @Convert(converter = UriConverter.class)
  @JsonProperty("baseUrl")
  public URI baseUrl;

  @Column(name = "api_key")
  @JsonProperty("apiKey")
  public String apiKey;

  @Column(name = "created_at", nullable = false, updatable = false)
  @JsonProperty("createdAt")
  public Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  @JsonProperty("updatedAt")
  public Instant updatedAt;

  @Column(name = "enabled", nullable = false)
  @JsonProperty("isEnabled")
  public boolean isEnabled;

  @RequiredForJPA
  public Connection() {}

  public Connection(ConnectionRequest request) {
    this.name = request.name();
    this.description = request.description();
    this.type = request.type();
    this.baseUrl = request.baseUrl();
    this.apiKey = request.apiKey();
  }

  public Connection mutateConnection(ConnectionRequest updateRequest) {
    this.name = updateRequest.name();
    this.description = updateRequest.description();
    this.type = updateRequest.type();
    this.baseUrl = updateRequest.baseUrl();
    this.apiKey = updateRequest.apiKey();
    this.isEnabled = updateRequest.isEnabled();
    return this;
  }

  @PrePersist
  public void prePersist() {
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = Instant.now();
  }
}
