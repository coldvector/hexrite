package io.codevector.hexrite.service.inference.ollama;

import io.codevector.hexrite.entity.chat.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OllamaPayloadBuilder {

  public JsonObject createPayloadPullModel(String model) {
    return new JsonObject().put("model", model).put("stream", true);
  }

  public JsonObject createPayloadDeleteModel(String model) {
    return new JsonObject().put("model", model);
  }

  public JsonObject createPayloadLoadModel(String model) {
    return new JsonObject().put("model", model).put("prompt", "");
  }

  public JsonObject createPayloadUnloadModel(String model) {
    return new JsonObject().put("model", model).put("keep_alive", 0);
  }

  public JsonObject createPayloadGenerateCompletion(String model, String prompt) {
    return new JsonObject().put("model", model).put("prompt", prompt);
  }

  public JsonObject createPayloadGenerateChatCompletion(String model, List<Message> messages) {
    JsonArray contents = new JsonArray();
    messages.forEach(m -> contents.add(messageToContent(m)));

    return new JsonObject().put("model", model).put("messages", contents);
  }

  private JsonObject messageToContent(Message message) {
    return new JsonObject()
        .put("role", message.role.toString().toLowerCase())
        .put("content", message.content);
  }
}
