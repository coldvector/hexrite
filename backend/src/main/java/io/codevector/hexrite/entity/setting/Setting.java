package io.codevector.hexrite.entity.setting;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.codevector.hexrite.annotations.RequiredForJPA;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.jboss.logging.Logger;

@Entity
@Table(name = "settings")
public class Setting extends PanacheEntityBase {

  private static final Logger LOG = Logger.getLogger(Setting.class.getSimpleName());

  @Id
  @RequiredForJPA
  @Column(nullable = false, unique = true)
  @JsonProperty("key")
  public String key;

  @Column(nullable = true)
  @JsonProperty("value")
  public String value;

  @RequiredForJPA
  public Setting() {}

  public Setting(Setting setting) {
    this.key = setting.key;
    this.value = setting.value;
  }
}
