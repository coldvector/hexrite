package io.codevector.hexrite.service.inference.ollama;

import io.codevector.hexrite.dto.inference.ollama.OllamaModel;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface OllamaService {

  public Uni<String> ping(String connectionId);

  public Uni<List<OllamaModel>> listLocalModels(String connectionId);

  public Uni<List<OllamaModel>> listRunningModels(String connectionId);

  public Uni<Void> pullModel(String connectionId, String model);

  public Uni<Void> loadModel(String connectionId, String model);
}
