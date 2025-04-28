package io.codevector.hexrite.exceptions;

import io.codevector.hexrite.dto.error.ErrorResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
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

  @ServerExceptionMapper({IllegalArgumentException.class, BadRequestException.class})
  public Response handleBadRequest(Throwable e) {
    return createErrorResponse(Response.Status.BAD_REQUEST, e.getMessage());
  }

  @ServerExceptionMapper(NotAllowedException.class)
  public Response handleMethodNotAllowed(NotAllowedException e) {
    return createErrorResponse(Response.Status.METHOD_NOT_ALLOWED, e.getMessage());
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
