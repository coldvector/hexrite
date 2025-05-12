package io.codevector.hexrite.client.inference.gemini;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Path("")
public interface GeminiClient {

  @GET
  @Path("/models")
  @Produces(MediaType.APPLICATION_JSON)
  Uni<JsonObject> listModels();

  @POST
  @Path("/models/{model}:streamGenerateContent")
  @ClientQueryParam(name = "alt", value = "sse")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @RestStreamElementType(MediaType.APPLICATION_JSON)
  Multi<JsonObject> generateContent(@PathParam("model") String model, JsonObject request);
}
