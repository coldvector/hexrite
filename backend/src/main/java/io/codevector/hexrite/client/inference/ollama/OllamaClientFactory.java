package io.codevector.hexrite.client.inference.ollama;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jboss.logging.Logger;

@ApplicationScoped
public class OllamaClientFactory {

  private static final Logger LOG = Logger.getLogger(OllamaClientFactory.class);

  private final Map<String, OllamaClient> clientCache = new ConcurrentHashMap<>();

  public OllamaClient getClient(String connectionId, String baseUrl) {
    return clientCache.computeIfAbsent(connectionId, id -> createClient(baseUrl));
  }

  private OllamaClient createClient(String baseUrl) {
    LOG.debugf("createClient: for \"%s\"", baseUrl);

    return QuarkusRestClientBuilder.newBuilder()
        .baseUri(URI.create(baseUrl))
        .build(OllamaClient.class);
  }
}
