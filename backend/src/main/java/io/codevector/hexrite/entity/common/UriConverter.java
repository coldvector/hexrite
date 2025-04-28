package io.codevector.hexrite.entity.common;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.net.URI;

@Converter(autoApply = true)
public class UriConverter implements AttributeConverter<URI, String> {

  @Override
  public String convertToDatabaseColumn(URI uri) {
    return uri == null ? "" : uri.toString();
  }

  @Override
  public URI convertToEntityAttribute(String dbData) {
    return URI.create("");
  }
}
