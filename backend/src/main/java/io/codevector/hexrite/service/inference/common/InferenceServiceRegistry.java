package io.codevector.hexrite.service.inference.common;

import io.codevector.hexrite.dto.connection.ConnectionType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class InferenceServiceRegistry {

  private final Map<ConnectionType, InferenceService> serviceMap = new HashMap<>();

  @Inject
  public InferenceServiceRegistry(Instance<InferenceService> inferenceServices) {
    this.serviceMap.putAll(
        inferenceServices.stream()
            .collect(Collectors.toMap(InferenceService::getType, Function.identity())));
  }

  public Optional<InferenceService> getInferenceService(ConnectionType type) {
    return Optional.ofNullable(serviceMap.get(type));
  }
}
