package io.codevector.hexrite.utils;

import io.codevector.hexrite.dto.error.ErrorResponse;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

@Singleton
public class UniUtils {

  public static Response handleResponse(Logger LOG, Response res) {
    LOG.debugf(
        "handleResponse: status=\"%d\", statusInfo=\"%s\", headers=\"%s\", startBuffer=\"%b\"",
        res.getStatus(), res.getStatusInfo().toEnum(), res.getHeaders(), res.bufferEntity());

    try {
      String body = res.readEntity(String.class);

      if (body == null || body.isBlank()) {
        // don't log empty body
      } else if (body.trim().startsWith("{") || body.trim().startsWith("[")) {
        JsonObject json = new JsonObject(body);
        LOG.debugf("handleResponse: body=\"%s\"", json.encode());
      } else {
        LOG.debugf("handleResponse: body=\"%s\"", body);
      }
    } catch (Exception e) {
      LOG.debugf("handleResponse: failed to read response body: \"%s\"", e.getMessage());
    }

    if (res.getStatus() >= 200 && res.getStatus() < 400) {
      return res;
    } else {
      throw new WebApplicationException(res);
    }
  }

  public static Throwable handleFailure(Logger LOG, Throwable t) {
    if (t instanceof WebApplicationException tw) {
      LOG.errorf(
          "handleFailure: statusCode=\"%s\", statusMessage=\"%s\", error=\"%s\"",
          tw.getResponse().getStatus(),
          tw.getResponse().getStatusInfo().getReasonPhrase(),
          tw.getLocalizedMessage());

      return new WebApplicationException(tw.getResponse());
    } else {
      LOG.errorf("handleFailure: error=\"%s\"", t.getLocalizedMessage());
      LOG.error("handleFailure: error", t);

      return new WebApplicationException(
          createErrorResponse(Status.SERVICE_UNAVAILABLE, t.getLocalizedMessage()));
    }
  }

  public static Throwable handleTimeout() {
    return new WebApplicationException(
        createErrorResponse(Status.GATEWAY_TIMEOUT, "Request timeout"));
  }

  public static Response handleSuccess(Object obj) {
    return Response.ok().entity(obj).build();
  }

  public static Response createErrorResponse(Status status, String message) {
    return Response.status(status).entity(ErrorResponse.create(message)).build();
  }
}
