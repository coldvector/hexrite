package io.codevector.hexrite.service.inference.gemini;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GeminiPayloadBuilder {

  public JsonObject createPayloadGenerateContent(String prompt) {
    return new JsonObject()
        .put(
            "contents",
            new JsonArray()
                .add(
                    new JsonObject()
                        .put("role", "user")
                        .put("parts", new JsonArray().add(new JsonObject().put("text", prompt)))));
  }
}
