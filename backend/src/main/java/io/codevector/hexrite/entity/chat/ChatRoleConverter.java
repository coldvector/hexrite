package io.codevector.hexrite.entity.chat;

import io.codevector.hexrite.dto.chat.ChatRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ChatRoleConverter implements AttributeConverter<ChatRole, String> {

  @Override
  public String convertToDatabaseColumn(ChatRole role) {
    return role == null ? ChatRole.USER.toString() : role.toString();
  }

  @Override
  public ChatRole convertToEntityAttribute(String dbData) {
    return ChatRole.fromString(dbData);
  }
}
