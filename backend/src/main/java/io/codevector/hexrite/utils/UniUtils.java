package io.codevector.hexrite.utils;

import io.codevector.hexrite.models.SimpleResponse;
import jakarta.inject.Singleton;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

/** Utility class for handling common Uni operations. */
@Singleton
public class UniUtils {

  /**
   * Handle success from service layer.
   *
   * @param obj object to be sent to client
   * @return Response with OK status and the object
   */
  public static Response handleSuccess(Object obj) {
    return Response.ok().entity(obj).build();
  }

  /**
   * Handle failure from service layer.
   *
   * @param LOG logger of the service
   * @param t exception thrown by service layer
   * @param status HTTP status code to be sent to client
   * @param message error message to be sent to client
   * @return WebApplicationException with appropriate status code and message
   */
  public static Throwable handleFailure(Logger LOG, Throwable t, Status status, String message) {
    LOG.errorf(t, "handleFailure: message=\"%s\", reason=\"%s\"", message, t.getLocalizedMessage());
    return new WebApplicationException(
        Response.status(status).entity(SimpleResponse.create(message)).build());
  }

  /**
   * Handle failure from service layer.
   *
   * @param LOG logger of the service
   * @param t exception thrown by service layer
   * @param message error message to be sent to client
   * @return WebApplicationException with appropriate status code and message
   */
  public static Throwable handleFailure(Logger LOG, Throwable t, String message) {
    LOG.errorf(t, "handleFailure: message=\"%s\", reason=\"%s\"", message, t.getLocalizedMessage());
    return new WebApplicationException(
        Response.status(Status.SERVICE_UNAVAILABLE).entity(SimpleResponse.create(message)).build());
  }

  /**
   * Handle timeouts from service layer.
   *
   * @return WebApplicationException with appropriate status code and message
   */
  public static Throwable handleTimeout() {
    return new WebApplicationException(
        Response.status(Response.Status.GATEWAY_TIMEOUT)
            .entity(SimpleResponse.create("Request timeout"))
            .build());
  }
}
