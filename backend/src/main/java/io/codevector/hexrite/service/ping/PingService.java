package io.codevector.hexrite.service.ping;

import io.smallrye.mutiny.Uni;

public interface PingService {

  Uni<String> ping();
}
