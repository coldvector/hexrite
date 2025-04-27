package io.codevector.hexrite.service.ping;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class PingServiceImpl implements PingService {

  private static final Logger LOG = Logger.getLogger(PingService.class.getSimpleName());

  @Override
  public Uni<String> ping() {
    LOG.debugf("ping");
    return Uni.createFrom().item("Hexrite is running");
  }
}
