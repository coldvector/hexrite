package io.codevector.hexrite.rest.common;

import io.codevector.hexrite.dto.error.ErrorResponse;
import io.codevector.hexrite.exceptions.ConflictException;
import io.codevector.hexrite.exceptions.ResourceNotFoundException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.jboss.resteasy.reactive.common.NotImplementedYet;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class ExceptionMapper {

  private static final Logger LOG = Logger.getLogger(ExceptionMapper.class.getSimpleName());

  @ServerExceptionMapper(ResourceNotFoundException.class)
  public Response handleResourceNotFound(ResourceNotFoundException e) {
    return createErrorResponse(Response.Status.NOT_FOUND, e.getMessage());
  }

  @ServerExceptionMapper(ConflictException.class)
  public Response handleConflict(ConflictException e) {
    return createErrorResponse(Response.Status.CONFLICT, e.getMessage());
  }

  @ServerExceptionMapper({
    IllegalArgumentException.class,
    BadRequestException.class,
    NotFoundException.class
  })
  public Response handleBadRequest(Throwable e) {
    return createErrorResponse(Response.Status.BAD_REQUEST, e.getMessage());
  }

  @ServerExceptionMapper(NotAllowedException.class)
  public Response handleMethodNotAllowed(NotAllowedException e) {
    return createErrorResponse(Response.Status.METHOD_NOT_ALLOWED, e.getMessage());
  }

  @ServerExceptionMapper(NotImplementedYet.class)
  public Response handleNotImplementedYet(NotImplementedYet e) {
    return createErrorResponse(Response.Status.NOT_IMPLEMENTED, e.getMessage());
  }

  @ServerExceptionMapper(ClientWebApplicationException.class)
  public Response handleClientWebAppliationException(ClientWebApplicationException e) {
    LOG.error("handleClientWebAppliationException: ", e);
    int status = e.getResponse().getStatus();
    String raw = e.getResponse().readEntity(String.class);

    String message;
    try {
      JsonObject json = Json.decodeValue(raw, JsonObject.class);

      if (json.containsKey("error")) {
        try {
          message = json.getJsonObject("error").getString("message");
        } catch (Exception ex) {
          message = json.getString("error");
        }
      } else if (json.containsKey("message")) {
        message = json.getString("message");
      } else {
        message = raw;
      }
    } catch (Exception ex) {
      message = raw;
    }
    return createErrorResponse(Response.Status.fromStatusCode(status), message);
  }

  @ServerExceptionMapper(Throwable.class)
  public Response handleGeneric(Throwable e) {
    LOG.error("Unexpected error: ", e);
    return createErrorResponse(Response.Status.SERVICE_UNAVAILABLE, e.getMessage());
  }

  private static Response createErrorResponse(Response.Status status, String message) {
    return Response.status(status).entity(ErrorResponse.create(message)).build();
  }
}
