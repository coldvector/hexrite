package io.codevector.hexrite.service.inference.gemini;

import io.codevector.hexrite.dto.chat.ChatRole;
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

  public JsonObject createPayloadChat(List<String> systemInstructions, List<Message> messages) {
    JsonArray contents = new JsonArray();
    messages.forEach(m -> contents.add(messageToContent(m)));

    JsonObject payload =
        new JsonObject()
            .put("contents", contents)
            .put("generationConfig", createTextGenerationConfig());

    if (systemInstructions != null && !systemInstructions.isEmpty()) {
      String systemInstructionsText = String.join(" ", systemInstructions);

      payload.put(
          "system_instruction",
          new JsonObject()
              .put(
                  "parts",
                  new JsonArray().add(new JsonObject().put("text", systemInstructionsText))));
    }

    return payload;
  }

  public JsonObject createTextGenerationConfig() {
    return new JsonObject().put("responseModalities", new JsonArray().add("TEXT"));
  }

  private JsonObject messageToContent(Message message) {
    return new JsonObject()
        .put(
            "role",
            message.role == ChatRole.ASSISTANT ? "model" : message.role.toString().toLowerCase())
        .put("parts", new JsonArray().add(new JsonObject().put("text", message.content)));
  }
}
