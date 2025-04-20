package io.codevector.voidbeacon.service;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public interface GreetingService {

  public Uni<Response> greet();

  public Uni<Response> error();
}
