package io.codevector.hexrite.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.Instant;

@MappedSuperclass
public abstract class AbstractTimestampedEntity extends PanacheEntityBase {

  @Column(name = "created_at", nullable = false, updatable = false)
  @JsonProperty("createdAt")
  public Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  @JsonProperty("updatedAt")
  public Instant updatedAt;

  @PrePersist
  public void onCreate() {
    // @CreationTimestamp not used because it is not JPA
    this.createdAt = Instant.now();
    this.updatedAt = this.createdAt;
  }

  @PreUpdate
  public void onUpdate() {
    // @UpdateTimestamp not used because it is not JPA
    this.updatedAt = Instant.now();
  }
}
