package io.codevector.hexrite.client.inference.gemini;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("")
public interface GeminiClient {

  @GET
  @Path("/models")
  @Produces(MediaType.APPLICATION_JSON)
  Uni<JsonObject> listModels();
}
