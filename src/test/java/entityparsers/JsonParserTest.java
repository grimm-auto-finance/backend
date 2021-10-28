package entityparsers;

import constants.Exceptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonParserTest {

  static JsonObjectBuilder builder;

  @BeforeAll
  static void setup() {
    builder = Json.createObjectBuilder();
    builder.add("Int value", 5);
    builder.add("String value", "hello");
    builder.add("Double value", 5.5);
    JsonObjectBuilder subObj = Json.createObjectBuilder();
    subObj.add("Sub-String value", "sub-string");
    builder.add("Sub-object", subObj);
  }

  @Test
  public void testJsonParser() {
    JsonParser parser = new JsonParser(builder.build());
    try {
      System.out.println(parser.parse());
    } catch (Exceptions.ParseException e) {
      fail();
    }
  }
}
