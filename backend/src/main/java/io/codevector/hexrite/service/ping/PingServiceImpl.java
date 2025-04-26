package io.codevector.hexrite.service.ping;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.logging.Logger;

@ApplicationScoped
public class PingServiceImpl implements PingService {

  private static final Logger LOG = Logger.getLogger(PingService.class.getSimpleName());

  @Override
  public Uni<Response> ping() {
    LOG.debugf("ping");

    Response res = Response.status(Status.OK).entity("Hexrite is running").build();

    return Uni.createFrom().item(res);
  }
}
