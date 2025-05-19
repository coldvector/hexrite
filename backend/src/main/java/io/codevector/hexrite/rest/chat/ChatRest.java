package io.codevector.hexrite.rest.chat;

import io.codevector.hexrite.rest.common.ResponseUtils;
import io.codevector.hexrite.service.chat.ChatService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Path("/v1/chat")
public class ChatRest {

  private final ChatService chatService;
  private final UriInfo uriInfo;

  @Inject
  public ChatRest(ChatService chatService, UriInfo uriInfo) {
    this.chatService = chatService;
    this.uriInfo = uriInfo;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> listChats() {
    return this.chatService
        .listChats(uriInfo.getQueryParameters())
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> getChatById(@PathParam("id") String chatId) {
    return this.chatService.getChatById(chatId).onItem().transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> createChat(JsonObject payload) {
    return this.chatService
        .createChat(payload.getString("connectionId"), payload.getString("model"))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @PATCH
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> updateChatTitle(@PathParam("id") String chatId, JsonObject payload) {
    return this.chatService
        .updateChatTitle(chatId, payload.getString("title", ""))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @RestStreamElementType(MediaType.TEXT_PLAIN)
  public Multi<JsonObject> chat(@PathParam("id") String chatId, JsonObject payload) {
    return this.chatService.chat(chatId, payload.getString("message", ""));
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> deleteChatById(@PathParam("id") String chatId) {
    return this.chatService.deleteChatById(chatId).onItem().transform(ResponseUtils::handleSuccess);
  }

  @DELETE
  public Uni<Response> deleteChats() {
    return this.chatService
        .deleteChats(uriInfo.getQueryParameters())
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }
}
