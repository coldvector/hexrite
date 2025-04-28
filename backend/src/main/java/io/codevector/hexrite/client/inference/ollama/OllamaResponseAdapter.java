package io.codevector.hexrite.client.inference.ollama;

import io.codevector.hexrite.dto.inference.ollama.OllamaModel;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OllamaResponseAdapter {

  List<OllamaModel> parseModelList(Response res) {
    JsonObject json = res.readEntity(JsonObject.class);
    if (json == null || json.isEmpty()) {
      return new ArrayList<>();
    }

    return json.getJsonArray("models").stream()
        .map(JsonObject.class::cast)
        .map(model -> parseModel(model))
        .collect(Collectors.toList());
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
