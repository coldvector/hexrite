package io.codevector.hexrite.client.common;

import io.codevector.hexrite.utils.JSONMapper;
import io.codevector.hexrite.utils.UniUtils;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.CompletionStageRxInvoker;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class JaxRsClient implements RestClient {

  private static final Logger LOG = Logger.getLogger(JaxRsClient.class.getSimpleName());

  private final Client client;
  private final Duration timeout;

  @Inject
  public JaxRsClient(@ConfigProperty(name = "timeout.secs", defaultValue = "5") long timeout) {
    this.client = ClientBuilder.newClient();
    this.timeout = Duration.ofSeconds(timeout);
  }

  @Override
  public Uni<Response> getRequest(URI uri, Map<String, String> headers) {
    LOG.debugf("getRequest: uri=\"%s\"", uri);

    return sendRequest(HttpMethod.GET, uri, headers, null);
  }

  @Override
  public Uni<Response> getRequest(URI uri, Map<String, String> headers, Object payload) {
    LOG.debugf("getRequest: uri=\"%s\", payload=\"%s\"", uri, JSONMapper.serialize(payload));

    return sendRequest(HttpMethod.GET, uri, headers, payload);
  }

  @Override
  public Uni<Response> postRequest(URI uri, Map<String, String> headers) {
    LOG.debugf("postRequest: uri=\"%s\"", uri);

    return sendRequest(HttpMethod.POST, uri, headers, null);
  }

  @Override
  public Uni<Response> postRequest(URI uri, Map<String, String> headers, Object payload) {
    LOG.debugf("postRequest: uri=\"%s\", payload=\"%s\"", uri, JSONMapper.serialize(payload));

    return sendRequest(HttpMethod.POST, uri, headers, payload);
  }

  @Override
  public Uni<Response> putRequest(URI uri, Map<String, String> headers) {
    LOG.debugf("putRequest: uri=\"%s\"", uri);

    return sendRequest(HttpMethod.PUT, uri, headers, null);
  }

  @Override
  public Uni<Response> putRequest(URI uri, Map<String, String> headers, Object payload) {
    LOG.debugf("putRequest: uri=\"%s\", payload=\"%s\"", uri, JSONMapper.serialize(payload));

    return sendRequest(HttpMethod.PUT, uri, headers, payload);
  }

  @Override
  public Uni<Response> patchRequest(URI uri, Map<String, String> headers) {
    LOG.debugf("patchRequest: uri=\"%s\"", uri);

    return sendRequest(HttpMethod.PATCH, uri, headers, null);
  }

  @Override
  public Uni<Response> patchRequest(URI uri, Map<String, String> headers, Object payload) {
    LOG.debugf("patchRequest: uri=\"%s\", payload=\"%s\"", uri, JSONMapper.serialize(payload));

    return sendRequest(HttpMethod.PATCH, uri, headers, payload);
  }

  @Override
  public Uni<Response> deleteRequest(URI uri, Map<String, String> headers) {
    LOG.debugf("deleteRequest: uri=\"%s\"", uri);

    return sendRequest(HttpMethod.DELETE, uri, headers, null);
  }

  @Override
  public Uni<Response> deleteRequest(URI uri, Map<String, String> headers, Object payload) {
    LOG.debugf("deleteRequest: uri=\"%s\", payload=\"%s\"", uri, JSONMapper.serialize(payload));

    return sendRequest(HttpMethod.DELETE, uri, headers, payload);
  }

  private Uni<Response> sendRequest(
      String method, URI uri, Map<String, String> headers, Object payload) {
    LOG.debugf(
        "%s request: uri=\"%s\", payload=\"%s\"", method, uri, JSONMapper.serialize(payload));

    Invocation.Builder request = client.target(uri).request();
    headers.forEach((k, v) -> request.header(k, v));
    CompletionStageRxInvoker invoker = request.rx();

    return Uni.createFrom()
        .completionStage(
            payload == null ? invoker.method(method) : invoker.method(method, Entity.json(payload)))
        .onItem()
        .transform(r -> UniUtils.handleResponse(LOG, r))
        .onFailure()
        .transform(t -> UniUtils.handleFailure(LOG, t))
        .ifNoItem()
        .after(timeout)
        .failWith(() -> UniUtils.handleTimeout());
  }
}
