package io.codevector.hexrite.service.inference.ollama;

import io.codevector.hexrite.dto.inference.ollama.OllamaModel;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import java.util.List;

public interface OllamaService {

  public Uni<String> ping(String connectionId);

  public Uni<List<OllamaModel>> listLocalModels(String connectionId);

  public Uni<List<OllamaModel>> listRunningModels(String connectionId);

  public Multi<JsonObject> pullModel(String connectionId, String model);

  public Uni<Void> deleteModel(String connectionId, String model);

  public Uni<Void> loadModel(String connectionId, String model);

  public Uni<Void> unloadModel(String connectionId, String model);
}
