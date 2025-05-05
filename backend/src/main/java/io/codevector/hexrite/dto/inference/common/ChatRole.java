package io.codevector.hexrite.dto.inference.common;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum ChatRole {
  USER,
  MODEL,
  SYSTEM,
  ASSISTANT,
  TOOL,
  ;

  public static ChatRole fromString(String role) {
    if (role == null || role.trim().isEmpty()) {
      return USER;
    }

    for (ChatRole cr : values()) {
      if (cr.name().equalsIgnoreCase(role.trim())) {
        return cr;
      }
    }

    return USER;
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
