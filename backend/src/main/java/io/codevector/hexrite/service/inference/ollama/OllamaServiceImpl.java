package io.codevector.hexrite.service.inference.ollama;

import io.codevector.hexrite.client.inference.ollama.OllamaClient;
import io.codevector.hexrite.client.inference.ollama.OllamaClientFactory;
import io.codevector.hexrite.dto.inference.ollama.OllamaModel;
import io.codevector.hexrite.service.connection.ConnectionService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.jboss.logging.Logger;

@ApplicationScoped
public class OllamaServiceImpl implements OllamaService {

  private static final Logger LOG = Logger.getLogger(OllamaService.class);

  private final ConnectionService connectionService;
  private final OllamaClientFactory clientFactory;
  private final OllamaPayloadBuilder payloadBuilder;
  private final OllamaResponseAdapter responseAdapter;

  @Inject
  public OllamaServiceImpl(
      ConnectionService connectionService,
      OllamaClientFactory clientFactory,
      OllamaPayloadBuilder payloadBuilder,
      OllamaResponseAdapter responseAdapter) {
    this.connectionService = connectionService;
    this.clientFactory = clientFactory;
    this.payloadBuilder = payloadBuilder;
    this.responseAdapter = responseAdapter;
  }

  @Override
  public Uni<String> ping(String connectionId) {
    LOG.infof("ping: \"%s\"", connectionId);
    return getOllamaClient(connectionId).chain(client -> client.ping());
  }

  @Override
  public Uni<List<OllamaModel>> listLocalModels(String connectionId) {
    LOG.infof("listLocalModels: \"%s\"", connectionId);
    return getOllamaClient(connectionId)
        .chain(client -> client.listLocalModels())
        .onItem()
        .transform(modelList -> responseAdapter.parseModelList(modelList));
  }

  @Override
  public Uni<List<OllamaModel>> listRunningModels(String connectionId) {
    LOG.infof("listRunningModels: \"%s\"", connectionId);
    return getOllamaClient(connectionId)
        .chain(client -> client.listRunningModels())
        .onItem()
        .transform(modelList -> responseAdapter.parseModelList(modelList));
  }

  @Override
  public Multi<JsonObject> pullModel(String connectionId, String model) {
    LOG.infof("pullModel: \"%s\"", connectionId);
    return getOllamaClient(connectionId)
        .onItem()
        .transformToMulti(client -> client.pullModel(payloadBuilder.createPayloadPullModel(model)));
  }

  @Override
  public Uni<Void> deleteModel(String connectionId, String model) {
    LOG.infof("deleteModel: \"%s\"", connectionId);
    return getOllamaClient(connectionId)
        .chain(client -> client.deleteModel(payloadBuilder.createPayloadDeleteModel(model)));
  }

  @Override
  public Uni<Void> loadModel(String connectionId, String model) {
    LOG.infof("loadModel: \"%s\"", connectionId);
    return getOllamaClient(connectionId)
        .chain(client -> client.loadModel(payloadBuilder.createPayloadLoadModel(model)))
        .onItem()
        .transform(j -> null);
  }

  @Override
  public Uni<Void> unloadModel(String connectionId, String model) {
    LOG.infof("unloadModel: \"%s\"", connectionId);
    return getOllamaClient(connectionId)
        .chain(client -> client.unloadModel(payloadBuilder.createPayloadUnloadModel(model)))
        .onItem()
        .transform(j -> null);
  }

  private Uni<OllamaClient> getOllamaClient(String connectionId) {
    return connectionService
        .getConnectionById(connectionId)
        .onItem()
        .transform(
            connection -> clientFactory.getClient(connectionId, connection.baseUrl.toString()));
  }
}
