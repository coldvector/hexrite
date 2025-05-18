package io.codevector.hexrite.client.inference.gemini;

import io.codevector.hexrite.client.common.AbstractClientFactory;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.UriBuilder;

@ApplicationScoped
public class GeminiClientFactory extends AbstractClientFactory<GeminiClient> {

  public GeminiClient getClient(String connectionId, String baseUrl, String apiKey) {
    return super.getClient(connectionId, () -> createClient(baseUrl, apiKey));
  }

  private GeminiClient createClient(String baseUrl, String apiKey) {
    return QuarkusRestClientBuilder.newBuilder()
        .baseUri(UriBuilder.fromUri(baseUrl).queryParam("key", apiKey).build())
        .build(GeminiClient.class);
  }
}
