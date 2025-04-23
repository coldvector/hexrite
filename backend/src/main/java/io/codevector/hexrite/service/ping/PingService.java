package io.codevector.hexrite.service.ping;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public interface PingService {

  public Uni<Response> ping();
}
