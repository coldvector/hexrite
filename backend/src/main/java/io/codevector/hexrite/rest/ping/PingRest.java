package io.codevector.hexrite.rest.ping;

import io.codevector.hexrite.service.ping.PingService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ping")
public class PingRest {

  private final PingService service;

  @Inject
  public PingRest(PingService service) {
    this.service = service;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> greet() {
    return this.service.ping();
  }
}
