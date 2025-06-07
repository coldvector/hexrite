package io.codevector.hexrite.service.inference.gemini;

import io.codevector.hexrite.dto.inference.gemini.GeminiModel;
import io.codevector.hexrite.entity.chat.Message;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import java.util.List;

public interface GeminiService {

  Uni<List<GeminiModel>> listModels(String connectionId);

  Multi<JsonObject> generateCompletion(String connectionId, String model, String prompt);

  Multi<JsonObject> generateChat(
      String connectionId,
      String model,
      List<String> systemInstructions,
      List<Message> messageList);
}
