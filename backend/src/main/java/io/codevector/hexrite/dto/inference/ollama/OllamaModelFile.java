package io.codevector.hexrite.dto.inference.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * See <a
 * href="https://github.com/ollama/ollama/blob/main/docs/modelfile.md#valid-parameters-and-values">modelfile.md</a>
 */
@RegisterForReflection
public record OllamaModelFile(
    @JsonProperty("num_ctx") int numCtx,
    @JsonProperty("repeat_last_n") int repeatLastN,
    @JsonProperty("repeat_penalty") double repeatPenalty,
    @JsonProperty("temperature") double temperature,
    @JsonProperty("seed") int seed,
    @JsonProperty("stop") String stop,
    @JsonProperty("num_predict") int numPredict,
    @JsonProperty("top_k") int topK,
    @JsonProperty("top_p") double topP,
    @JsonProperty("min_p") double minP) {

  public static final OllamaModelFile DEFAULT =
      new OllamaModelFile(2048, 64, 1.1, 0.8, 0, "", -1, 40, 0.9, 0.0);
}
