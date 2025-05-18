package io.codevector.hexrite.client.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.jboss.logging.Logger;

public abstract class AbstractClientFactory<T> {

  protected final Logger LOG = Logger.getLogger(getClass());

  private final Map<String, T> clientCache = new ConcurrentHashMap<>();

  public T getClient(String connectionId, Supplier<T> clientSupplier) {
    return clientCache.computeIfAbsent(
        connectionId,
        id -> {
          LOG.debugf("createClient: for connectionId \"%s\"", connectionId);
          return clientSupplier.get();
        });
  }
}
