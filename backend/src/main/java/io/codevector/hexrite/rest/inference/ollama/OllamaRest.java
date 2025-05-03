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

  @Path("/list")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> listLocalModels(JsonObject payload) {
    return ollamaService
        .listLocalModels(payload.getString("connectionId"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @Path("/ps")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> listRunningModels(JsonObject payload) {
    return ollamaService
        .listRunningModels(payload.getString("connectionId"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @Path("/pull")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @RestStreamElementType(MediaType.APPLICATION_JSON)
  public Multi<JsonObject> pullModel(JsonObject payload) {
    return ollamaService.pullModel(payload.getString("connectionId"), payload.getString("model"));
  }

  @Path("/delete")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> deleteModel(JsonObject payload) {
    return ollamaService
        .deleteModel(payload.getString("connectionId"), payload.getString("model"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @Path("/load")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> loadModel(JsonObject payload) {
    return ollamaService
        .loadModel(payload.getString("connectionId"), payload.getString("model"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @Path("/unload")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> unloadModel(JsonObject payload) {
    return ollamaService
        .unloadModel(payload.getString("connectionId"), payload.getString("model"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }
}
