package io.codevector.hexrite.client.inference.gemini;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriBuilder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jboss.logging.Logger;

@ApplicationScoped
public class GeminiClientFactory {

  private static final Logger LOG = Logger.getLogger(GeminiClientFactory.class);

  private final Map<String, GeminiClient> clientCache = new ConcurrentHashMap<>();

  public GeminiClient getClient(String connectionId, String baseUrl, String apiKey) {
    return clientCache.computeIfAbsent(connectionId, id -> createClient(baseUrl, apiKey));
  }

  private GeminiClient createClient(String baseUrl, String apiKey) {
    LOG.debugf("createClient: for \"%s\"", baseUrl);

    return QuarkusRestClientBuilder.newBuilder()
        .baseUri(UriBuilder.fromUri(baseUrl).queryParam("key", apiKey).build())
        .build(GeminiClient.class);
  }
}
