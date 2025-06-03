package io.codevector.hexrite.rest.project;

import io.codevector.hexrite.rest.common.ResponseUtils;
import io.codevector.hexrite.service.project.ProjectService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/v1/project")
public class ProjectRest {

  private final ProjectService projectService;

  @Inject
  public ProjectRest(ProjectService projectService, UriInfo uriInfo) {
    this.projectService = projectService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> listProjects() {
    return this.projectService.listProjects().onItem().transform(ResponseUtils::handleSuccess);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> getProjectById(@PathParam("id") String projectId) {
    return this.projectService
        .getProjectWithChatsById(projectId)
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> createProject(JsonObject payload) {
    return this.projectService
        .createProject(payload.getString("title"), payload.getString("context"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @PATCH
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> updateProject(@PathParam("id") String projectId, JsonObject payload) {
    return this.projectService
        .updateProject(projectId, payload.getString("title"), payload.getString("context"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> deleteProjectById(@PathParam("id") String projectId) {
    return this.projectService
        .deleteProjectById(projectId)
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }
}
