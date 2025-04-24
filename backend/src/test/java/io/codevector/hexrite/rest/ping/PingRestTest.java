package io.codevector.hexrite.rest.ping;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class PingRestTest {

  @Test
  void testPing() {
    given().when().get("/ping").then().statusCode(200).contentType(MediaType.APPLICATION_JSON);
  }
}
