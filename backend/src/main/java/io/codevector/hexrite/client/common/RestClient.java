package io.codevector.hexrite.client.common;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;

public interface RestClient {

  public Uni<Response> getRequest(URI uri, Map<String, String> headers);

  public Uni<Response> postRequest(URI uri, Map<String, String> headers, Object payload);

  public Uni<Response> putRequest(URI uri, Map<String, String> headers, Object payload);

  public Uni<Response> patchRequest(URI uri, Map<String, String> headers, Object payload);

  public Uni<Response> deleteRequest(URI uri, Map<String, String> headers);
}
