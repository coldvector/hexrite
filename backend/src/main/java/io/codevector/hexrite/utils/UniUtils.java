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
        "handleResponse: status=\"%s\", headers=\"%s\", startBuffer=\"%b\", body=\"%s\"",
        res.getStatus(),
        res.getHeaders(),
        res.bufferEntity(),
        JSONMapper.serialize(res.readEntity(JsonObject.class)));

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
    return Response.status(status).entity(ErrorResponse.create(status, message)).build();
  }
}
