package io.codevector.hexrite.dto.connection;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum ConnectionType {
  OLLAMA,
  CHATGPT,
  GEMINI,
  CLAUDE,
  MISTRAL,
  OPENAI_COMPATIBLE,
  CUSTOM,
  ;

  public static ConnectionType fromString(String name) {
    if (name == null || name.isBlank()) {
      return null;
    }

    for (ConnectionType model : values()) {
      if (model.name().equalsIgnoreCase(name.trim())) {
        return model;
      }
    }

    return null;
  }
}
