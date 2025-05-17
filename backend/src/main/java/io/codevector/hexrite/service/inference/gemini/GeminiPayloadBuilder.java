package io.codevector.hexrite.service.inference.gemini;

import io.codevector.hexrite.entity.chat.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

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

  public JsonObject createPayloadChat(List<Message> messages) {
    JsonArray payload = new JsonArray();
    messages.forEach(m -> payload.add(messageToContent(m)));
    return new JsonObject()
        .put("contents", payload)
        .put("generationConfig", createTextGenerationConfig());
  }

  public JsonObject createTextGenerationConfig() {
    return new JsonObject().put("responseModalities", new JsonArray().add("TEXT"));
  }

  private JsonObject messageToContent(Message message) {
    return new JsonObject()
        .put("role", message.role)
        .put("parts", new JsonArray().add(new JsonObject().put("text", message.content)));
  }
}
