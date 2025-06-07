package io.codevector.hexrite.service.inference.common;

import io.codevector.hexrite.dto.connection.ConnectionType;
import io.codevector.hexrite.entity.chat.Message;
import io.smallrye.mutiny.Multi;
import io.vertx.core.json.JsonObject;
import java.util.List;

public interface InferenceService {

  ConnectionType getType();

  Multi<JsonObject> generateCompletion(String connectionId, String model, String prompt);

  Multi<JsonObject> generateChat(
      String connectionId,
      String model,
      List<String> systemInstructions,
      List<Message> messageList);
}
