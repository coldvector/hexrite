package io.codevector.hexrite.client.inference.ollama;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Path("")
public interface OllamaClient {

  String ND_JSON = "application/x-ndjson";

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  Uni<String> ping();

  @GET
  @Path("/api/tags")
  @Produces(MediaType.APPLICATION_JSON)
  Uni<JsonObject> listLocalModels();

  @GET
  @Path("/api/ps")
  @Produces(MediaType.APPLICATION_JSON)
  Uni<JsonObject> listRunningModels();

  @POST
  @Path("/api/pull")
  @Consumes(MediaType.APPLICATION_JSON)
  @RestStreamElementType(MediaType.APPLICATION_JSON)
  Multi<JsonObject> pullModel(JsonObject request);

  @DELETE
  @Path("/api/delete")
  @Consumes(MediaType.APPLICATION_JSON)
  Uni<Void> deleteModel(JsonObject request);

  @POST
  @Path("/api/generate")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  Uni<JsonObject> loadModel(JsonObject request);

  @POST
  @Path("/api/generate")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  Uni<JsonObject> unloadModel(JsonObject request);

  @POST
  @Path("/api/generate")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(ND_JSON)
  Multi<String> generateCompletion(JsonObject request);
}
