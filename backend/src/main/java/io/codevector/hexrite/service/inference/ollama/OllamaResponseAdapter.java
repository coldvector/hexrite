package io.codevector.hexrite.service.inference.ollama;

import io.codevector.hexrite.dto.inference.common.InferenceChunk;
import io.codevector.hexrite.dto.inference.ollama.OllamaModel;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OllamaResponseAdapter {

  List<OllamaModel> parseModelList(JsonObject json) {
    if (json == null || json.isEmpty()) {
      return new ArrayList<>();
    }

    return json.getJsonArray("models").stream()
        .map(JsonObject.class::cast)
        .map(model -> parseModel(model))
        .collect(Collectors.toList());
  }

  JsonObject parseStreamingGenerationResponseChunk(JsonObject json) {
    return JsonObject.mapFrom(new InferenceChunk(json.getString("response")));
  }

  JsonObject parseStreamingChatGenerationResponseChunk(JsonObject json) {
    return JsonObject.mapFrom(
        new InferenceChunk(json.getJsonObject("message").getString("content")));
  }

  private OllamaModel parseModel(JsonObject json) {
    return new OllamaModel(
        json.getString("name"),
        json.getString("model"),
        json.getLong("size"),
        json.getString("digest"),
        json.getJsonObject("details").getString("parent_model"),
        json.getJsonObject("details").getString("format"),
        json.getJsonObject("details").getString("family"),
        json.getJsonObject("details").getString("parameter_size"),
        json.getJsonObject("details").getString("quantization_level"));
  }
}
