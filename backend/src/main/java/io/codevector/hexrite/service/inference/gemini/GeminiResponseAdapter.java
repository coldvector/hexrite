package io.codevector.hexrite.service.inference.gemini;

import io.codevector.hexrite.dto.inference.common.InferenceChunk;
import io.codevector.hexrite.dto.inference.gemini.GeminiModel;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GeminiResponseAdapter {

  List<GeminiModel> parseModelList(JsonObject json) {
    if (json == null || json.isEmpty()) {
      return new ArrayList<>();
    }

    return json.getJsonArray("models").stream()
        .map(JsonObject.class::cast)
        .map(model -> parseModel(model))
        .collect(Collectors.toList());
  }

  JsonObject parseStreamingResponseChunk(JsonObject json) {
    return JsonObject.mapFrom(
        new InferenceChunk(
            json.getJsonArray("candidates")
                .getJsonObject(0)
                .getJsonObject("content")
                .getJsonArray("parts")
                .getJsonObject(0)
                .getString("text")));
  }

  private GeminiModel parseModel(JsonObject json) {
    return new GeminiModel(
        json.getString("name", "").replaceFirst("^models/", ""),
        json.getString("displayName", ""),
        json.getString("description", ""),
        json.getLong("inputTokenLimit", 0L),
        json.getLong("outputTokenLimit", 0L),
        json.getFloat("temperature", 0f),
        json.getFloat("topP", 0f),
        json.getFloat("topK", 0f),
        json.getFloat("maxTemperature", 0f));
  }
}
