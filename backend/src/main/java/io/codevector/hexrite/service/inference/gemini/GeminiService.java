package io.codevector.hexrite.service.inference.gemini;

import io.codevector.hexrite.dto.inference.gemini.GeminiModel;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface GeminiService {

  public Uni<List<GeminiModel>> listModels(String connectionId);
}
