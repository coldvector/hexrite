package io.codevector.hexrite.client;

import io.codevector.hexrite.utils.JSONMapper;
import io.codevector.hexrite.utils.UniUtils;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.CompletionStageRxInvoker;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
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
  public Uni<Response> getRequest(String uri, Map<String, String> headers) {
    LOG.debugf("getRequest: uri=\"%s\"", uri);

    CompletionStageRxInvoker invoker = this.client.target(uri).request().rx();

    return Uni.createFrom()
        .completionStage(invoker.get())
        .onItem()
        .transform(r -> UniUtils.handleResponse(LOG, r))
        .onFailure()
        .transform(t -> UniUtils.handleFailure(LOG, t))
        .ifNoItem()
        .after(this.timeout)
        .failWith(() -> UniUtils.handleTimeout());
  }

  @Override
  public Uni<Response> postRequest(String uri, Map<String, String> headers, Object payload) {
    LOG.debugf("postRequest: uri=\"%s\", payload=\"%s\"", uri, JSONMapper.serialize(payload));

    CompletionStageRxInvoker invoker = this.client.target(uri).request().rx();

    return Uni.createFrom()
        .completionStage(invoker.post(Entity.json(payload)))
        .onItem()
        .transform(r -> UniUtils.handleResponse(LOG, r))
        .onFailure()
        .transform(t -> UniUtils.handleFailure(LOG, t))
        .ifNoItem()
        .after(this.timeout)
        .failWith(() -> UniUtils.handleTimeout());
  }

  @Override
  public Uni<Response> putRequest(String uri, Map<String, String> headers, Object payload) {
    LOG.debugf("putRequest: uri=\"%s\", payload=\"%s\"", uri, JSONMapper.serialize(payload));

    CompletionStageRxInvoker invoker = this.client.target(uri).request().rx();

    return Uni.createFrom()
        .completionStage(invoker.put(Entity.json(payload)))
        .onItem()
        .transform(r -> UniUtils.handleResponse(LOG, r))
        .onFailure()
        .transform(t -> UniUtils.handleFailure(LOG, t))
        .ifNoItem()
        .after(this.timeout)
        .failWith(() -> UniUtils.handleTimeout());
  }

  @Override
  public Uni<Response> patchRequest(String uri, Map<String, String> headers, Object payload) {
    LOG.debugf("patchRequest: uri=\"%s\", payload=\"%s\"", uri, JSONMapper.serialize(payload));

    CompletionStageRxInvoker invoker = this.client.target(uri).request().rx();

    return Uni.createFrom()
        .completionStage(invoker.method("PATCH", Entity.json(payload)))
        .onItem()
        .transform(r -> UniUtils.handleResponse(LOG, r))
        .onFailure()
        .transform(t -> UniUtils.handleFailure(LOG, t))
        .ifNoItem()
        .after(this.timeout)
        .failWith(() -> UniUtils.handleTimeout());
  }

  @Override
  public Uni<Response> deleteRequest(String uri, Map<String, String> headers) {
    LOG.debugf("deleteRequest: uri=\"%s\"", uri);

    CompletionStageRxInvoker invoker = this.client.target(uri).request().rx();

    return Uni.createFrom()
        .completionStage(invoker.delete())
        .onItem()
        .transform(r -> UniUtils.handleResponse(LOG, r))
        .onFailure()
        .transform(t -> UniUtils.handleFailure(LOG, t))
        .ifNoItem()
        .after(this.timeout)
        .failWith(() -> UniUtils.handleTimeout());
  }
}
