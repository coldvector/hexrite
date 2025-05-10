package io.codevector.hexrite.rest.chat;

import io.codevector.hexrite.rest.common.ResponseUtils;
import io.codevector.hexrite.service.chat.ChatService;
import io.codevector.hexrite.service.chat.ChatServiceImpl;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/chat")
public class ChatRest {

  private final ChatService chatService;

  @Inject
  public ChatRest(ChatServiceImpl chatService) {
    this.chatService = chatService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> listChats() {
    return this.chatService
        .listChats()
        .onItem()
        .transform(list -> ResponseUtils.handleSuccess(list));
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  public Uni<Response> getChatById(@PathParam("id") String chatId) {
    return this.chatService.getChatById(chatId).onItem().transform(ResponseUtils::handleSuccess);
  }
}
