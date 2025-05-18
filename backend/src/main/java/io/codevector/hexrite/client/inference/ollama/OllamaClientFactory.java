package io.codevector.hexrite.client.inference.ollama;

import io.codevector.hexrite.client.common.AbstractClientFactory;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import java.net.URI;

@ApplicationScoped
public class OllamaClientFactory extends AbstractClientFactory<OllamaClient> {

  public OllamaClient getClient(String connectionId, String baseUrl) {
    return getClient(connectionId, () -> createClient(baseUrl));
  }

  private OllamaClient createClient(String baseUrl) {
    return QuarkusRestClientBuilder.newBuilder()
        .baseUri(URI.create(baseUrl))
        .build(OllamaClient.class);
  }
}
