package io.codevector.hexrite.client.inference.ollama;

import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OllamaPayloadBuilder {

  public JsonObject createPayloadPullModel(String model, boolean streamResponse) {
    return new JsonObject().put("model", model).put("stream", streamResponse);
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
}
