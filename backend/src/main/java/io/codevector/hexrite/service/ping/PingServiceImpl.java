package io.codevector.hexrite.service.ping;

import io.codevector.hexrite.models.SimpleResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

@ApplicationScoped
public class PingServiceImpl implements PingService {

  private static final Logger LOGGER = Logger.getLogger(PingService.class.getSimpleName());

  @Override
  public Uni<Response> ping() {
    LOGGER.debugf("ping");

    Response res = Response.status(Status.OK).entity(SimpleResponse.create("Hello!")).build();

    return Uni.createFrom().item(res);
  }
}
