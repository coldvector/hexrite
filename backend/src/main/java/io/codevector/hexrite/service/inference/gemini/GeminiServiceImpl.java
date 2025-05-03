package io.codevector.hexrite.service.inference.gemini;

import io.codevector.hexrite.client.inference.gemini.GeminiClient;
import io.codevector.hexrite.client.inference.gemini.GeminiClientFactory;
import io.codevector.hexrite.dto.inference.gemini.GeminiModel;
import io.codevector.hexrite.service.connection.ConnectionService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.jboss.logging.Logger;

@ApplicationScoped
public class GeminiServiceImpl implements GeminiService {

  private static final Logger LOG = Logger.getLogger(GeminiService.class);

  private final ConnectionService connectionService;
  private final GeminiClientFactory clientFactory;
  private final GeminiPayloadBuilder payloadBuilder;
  private final GeminiResponseAdapter responseAdapter;

  @Inject
  public GeminiServiceImpl(
      ConnectionService connectionService,
      GeminiClientFactory clientFactory,
      GeminiPayloadBuilder payloadBuilder,
      GeminiResponseAdapter responseAdapter) {
    this.connectionService = connectionService;
    this.clientFactory = clientFactory;
    this.payloadBuilder = payloadBuilder;
    this.responseAdapter = responseAdapter;
  }

  @Override
  public Uni<List<GeminiModel>> listModels(String connectionId) {
    LOG.infof("listModels: \"%s\"", connectionId);
    return getGeminiClient(connectionId)
        .chain(client -> client.listModels())
        .onItem()
        .transform(modelList -> responseAdapter.parseModelList(modelList));
  }

  @Override
  public Multi<JsonObject> generateContent(String connectionId, String model, String prompt) {
    LOG.infof("generateCompletion: \"%s\", \"%s\"", connectionId, model);
    return getGeminiClient(connectionId)
        .onItem()
        .transformToMulti(
            client ->
                client.generateContent(model, payloadBuilder.createPayloadGenerateContent(prompt)));
  }

  private Uni<GeminiClient> getGeminiClient(String connectionId) {
    return connectionService
        .getConnectionById(connectionId)
        .onItem()
        .transform(
            connection ->
                clientFactory.getClient(
                    connectionId, connection.baseUrl.toString(), connection.apiKey));
  }
}
