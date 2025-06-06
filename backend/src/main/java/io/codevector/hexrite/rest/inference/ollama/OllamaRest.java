package io.codevector.hexrite.rest.inference.ollama;

import io.codevector.hexrite.rest.common.ResponseUtils;
import io.codevector.hexrite.service.inference.ollama.OllamaService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Path("/v1/inference/ollama")
public class OllamaRest {

  private final OllamaService ollamaService;

  @Inject
  public OllamaRest(OllamaService ollamaService) {
    this.ollamaService = ollamaService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> ping(JsonObject payload) {
    return ollamaService
        .ping(payload.getString("connectionId"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Path("/list")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> listModels(JsonObject payload) {
    return ollamaService
        .listModels(payload.getString("connectionId"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Path("/ps")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> listRunningModels(JsonObject payload) {
    return ollamaService
        .listRunningModels(payload.getString("connectionId"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Path("/pull")
  @Consumes(MediaType.APPLICATION_JSON)
  @RestStreamElementType(MediaType.APPLICATION_JSON)
  public Multi<JsonObject> pullModel(JsonObject payload) {
    return ollamaService.pullModel(payload.getString("connectionId"), payload.getString("model"));
  }

  @POST
  @Path("/delete")
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> deleteModel(JsonObject payload) {
    return ollamaService
        .deleteModel(payload.getString("connectionId"), payload.getString("model"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Path("/load")
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> loadModel(JsonObject payload) {
    return ollamaService
        .loadModel(payload.getString("connectionId"), payload.getString("model"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Path("/unload")
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> unloadModel(JsonObject payload) {
    return ollamaService
        .unloadModel(payload.getString("connectionId"), payload.getString("model"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Path("/generate")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @RestStreamElementType(MediaType.TEXT_PLAIN)
  public Multi<JsonObject> generateCompletion(JsonObject payload) {
    return ollamaService.generateCompletion(
        payload.getString("connectionId"), payload.getString("model"), payload.getString("prompt"));
  }
}
