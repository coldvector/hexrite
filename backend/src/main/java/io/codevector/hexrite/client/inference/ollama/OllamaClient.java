package io.codevector.hexrite.client.inference.ollama;

import io.codevector.hexrite.client.common.RestClient;
import io.codevector.hexrite.dto.inference.ollama.OllamaModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import java.net.URI;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class OllamaClient {

  private final RestClient restClient;
  private final OllamaResponseAdapter responseAdapter;
  private final OllamaPayloadBuilder payloadBuilder;

  @Inject
  public OllamaClient(
      RestClient restClient,
      OllamaResponseAdapter responseAdapter,
      OllamaPayloadBuilder payloadBuilder) {
    this.restClient = restClient;
    this.responseAdapter = responseAdapter;
    this.payloadBuilder = payloadBuilder;
  }

  public Uni<String> ping(URI uri) {
    return restClient
        .getRequest(uri, createHeadersText())
        .onItem()
        .transform(res -> res.readEntity(String.class));
  }

  public Uni<List<OllamaModel>> listLocalModels(URI uri) {
    return restClient
        .getRequest(uri, createHeadersJson())
        .onItem()
        .transform(res -> responseAdapter.parseModelList(res));
  }

  public Uni<List<OllamaModel>> listRunningModels(URI uri) {
    return restClient
        .getRequest(uri, createHeadersJson())
        .onItem()
        .transform(res -> responseAdapter.parseModelList(res));
  }

  public Uni<Void> pullModel(URI uri, String model, boolean streamResponse) {
    return restClient
        .postRequest(
            uri, createHeaders(), payloadBuilder.createPayloadPullModel(model, streamResponse))
        .onItem()
        .transform(res -> res.readEntity(Void.class));
  }

  public Uni<Void> deleteModel(URI uri, String model, boolean streamResponse) {
    return restClient
        .deleteRequest(uri, createHeaders(), payloadBuilder.createPayloadDeleteModel(model))
        .onItem()
        .transform(res -> res.readEntity(Void.class));
  }

  public Uni<Void> loadModel(URI uri, String model, boolean streamResponse) {
    return restClient
        .postRequest(uri, createHeaders(), payloadBuilder.createPayloadLoadModel(model))
        .onItem()
        .transform(res -> res.readEntity(Void.class));
  }

  public Uni<Void> unloadModel(URI uri, String model, boolean streamResponse) {
    return restClient
        .postRequest(uri, createHeaders(), payloadBuilder.createPayloadUnloadModel(model))
        .onItem()
        .transform(res -> res.readEntity(Void.class));
  }

  private Map<String, String> createHeadersJson() {
    return Map.of("Accept", MediaType.APPLICATION_JSON);
  }

  private Map<String, String> createHeadersText() {
    return Map.of("Accept", MediaType.TEXT_PLAIN);
  }

  private Map<String, String> createHeaders() {
    return Map.of("Content-Type", MediaType.APPLICATION_JSON, "Accept", MediaType.APPLICATION_JSON);
  }
}
