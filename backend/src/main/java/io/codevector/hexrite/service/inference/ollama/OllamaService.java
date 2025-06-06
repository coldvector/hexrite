package io.codevector.hexrite.service.inference.ollama;

import io.codevector.hexrite.dto.inference.ollama.OllamaModel;
import io.codevector.hexrite.entity.chat.Message;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import java.util.List;

public interface OllamaService {

  Uni<String> ping(String connectionId);

  Uni<List<OllamaModel>> listModels(String connectionId);

  Uni<List<OllamaModel>> listRunningModels(String connectionId);

  Multi<JsonObject> pullModel(String connectionId, String model);

  Uni<Void> deleteModel(String connectionId, String model);

  Uni<Void> loadModel(String connectionId, String model);

  Uni<Void> unloadModel(String connectionId, String model);

  Multi<JsonObject> generateCompletion(String connectionId, String model, String prompt);

  Multi<JsonObject> generateChat(
      String connectionId,
      String model,
      List<String> systemInstructions,
      List<Message> messageList);
}
