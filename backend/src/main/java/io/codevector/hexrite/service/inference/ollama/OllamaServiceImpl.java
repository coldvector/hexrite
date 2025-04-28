package io.codevector.hexrite.service.inference.ollama;

import io.codevector.hexrite.client.inference.ollama.OllamaClient;
import io.codevector.hexrite.dto.inference.ollama.OllamaModel;
import io.codevector.hexrite.service.connection.ConnectionService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OllamaServiceImpl implements OllamaService {

  private final OllamaClient restClient;
  private final ConnectionService connectionService;
  private final boolean streamResponse = false;

  @Inject
  public OllamaServiceImpl(OllamaClient restClient, ConnectionService connectionService) {
    this.restClient = restClient;
    this.connectionService = connectionService;
  }

  public Uni<String> ping(String connectionId) {
    return connectionService
        .getConnectionById(connectionId)
        .chain(conn -> restClient.ping(conn.baseUrl));
  }

  public Uni<List<OllamaModel>> listLocalModels(String connectionId) {
    return connectionService
        .getConnectionById(connectionId)
        .chain(conn -> restClient.listLocalModels(URI.create(conn.baseUrl + "/api/tags")));
  }

  public Uni<List<OllamaModel>> listRunningModels(String connectionId) {
    return connectionService
        .getConnectionById(connectionId)
        .chain(conn -> restClient.listRunningModels(URI.create(conn.baseUrl + "/api/ps")));
  }

  public Uni<Void> pullModel(String connectionId, String model) {
    return connectionService
        .getConnectionById(connectionId)
        .chain(
            conn ->
                restClient.pullModel(
                    URI.create(conn.baseUrl + "/api/pull"), model, streamResponse));
  }

  public Uni<Void> loadModel(String connectionId, String model) {
    return connectionService
        .getConnectionById(connectionId)
        .chain(
            conn ->
                restClient.loadModel(
                    URI.create(conn.baseUrl + "/api/generate"), model, streamResponse));
  }

  private URI createURL(URI baseUrl, String... path) {
    if (baseUrl == null || path == null || path.length == 0) {
      return baseUrl;
    }

    String basePath = baseUrl.toString();
    if (!basePath.endsWith("/")) {
      basePath += "/";
    }

    String finalPath =
        Arrays.stream(path)
            .map(p -> p.startsWith("/") ? p.substring(1) : p) // Strip leading slashes
            .collect(Collectors.joining("/"));

    return URI.create(basePath + finalPath);
  }
}
