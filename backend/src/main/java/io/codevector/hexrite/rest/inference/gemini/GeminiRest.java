package io.codevector.hexrite.rest.inference.gemini;

import io.codevector.hexrite.rest.common.ResponseUtils;
import io.codevector.hexrite.service.inference.gemini.GeminiService;
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

@Path("/v1/inference/gemini")
public class GeminiRest {

  private final GeminiService geminiService;

  @Inject
  public GeminiRest(GeminiService geminiService) {
    this.geminiService = geminiService;
  }

  @POST
  @Path("/list")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> listModels(JsonObject payload) {
    return geminiService
        .listModels(payload.getString("connectionId"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Path("/generate")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @RestStreamElementType(MediaType.TEXT_PLAIN)
  public Multi<JsonObject> generateCompletion(JsonObject payload) {
    return geminiService.generateCompletion(
        payload.getString("connectionId"), payload.getString("model"), payload.getString("prompt"));
  }
}
