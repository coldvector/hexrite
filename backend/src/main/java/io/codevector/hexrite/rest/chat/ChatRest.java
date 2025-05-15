package io.codevector.hexrite.rest.chat;

import io.codevector.hexrite.dto.chat.ChatRequest;
import io.codevector.hexrite.rest.common.ResponseUtils;
import io.codevector.hexrite.service.chat.ChatService;
import io.codevector.hexrite.service.chat.ChatServiceImpl;
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
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/v1/chat")
public class ChatRest {

  private final ChatService chatService;
  private final UriInfo uriInfo;

  @Inject
  public ChatRest(ChatServiceImpl chatService, UriInfo uriInfo) {
    this.chatService = chatService;
    this.uriInfo = uriInfo;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> listChats() {
    MultivaluedMap<String, String> filter = uriInfo.getQueryParameters();

    return this.chatService
        .listChats(filter)
        .onItem()
        .transform(list -> ResponseUtils.handleSuccess(list));
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  public Uni<Response> getChatById(@PathParam("id") String chatId) {
    return this.chatService.getChatById(chatId).onItem().transform(ResponseUtils::handleSuccess);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Uni<Response> createChat(ChatRequest request) {
    return this.chatService
        .createChat(request.connectionId(), request.model())
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @PATCH
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{id}/updateTitle")
  public Uni<Response> updateChatTitle(@PathParam("id") String chatId, JsonObject payload) {
    return this.chatService
        .updateChatTitle(chatId, payload.getString("title", ""))
        .onItem()
        .transform(ResponseUtils::handleSuccess);
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> deleteChat(@PathParam("id") String chatId) {
    return this.chatService.deleteChatById(chatId).onItem().transform(ResponseUtils::handleSuccess);
  }
}
