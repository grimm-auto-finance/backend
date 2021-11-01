package entityparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.Car;

import entities.CarBuyer;
import entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ParseCarUseCaseTest {
  @Test
  void parse() {
    JsonObject obj =
        Json.createObjectBuilder()
            .add(
                EntityStringNames.CAR_STRING,
                Json.createObjectBuilder()
                    .add(EntityStringNames.CAR_PRICE, 10000.0)
                    .add(EntityStringNames.CAR_MAKE, "Honda")
                    .add(EntityStringNames.CAR_MODEL, "Civic")
                    .add(EntityStringNames.CAR_YEAR, 2002)
                    .build())
            .build();
    Parser jsonParser = new JsonParser(obj);
    ParseCarUseCase parseCarUseCase;
    Car parsed = null;
    try {
      parseCarUseCase = new ParseCarUseCase(jsonParser);
      parsed = parseCarUseCase.parse();
    } catch (Exceptions.ParseException e) {
      fail();
    }
    Car car = new Car(10000.0, "Honda", "Civic", 2002);
    assertEquals(car.getAddOns(), parsed.getAddOns());
    assertEquals(car.getMake(), parsed.getMake());
    assertEquals(car.getModel(), parsed.getModel());
    assertEquals(car.getPrice(), parsed.getPrice());
    assertEquals(car.getYear(), parsed.getYear());
  }

  static JsonObjectBuilder builder;

  @BeforeEach
  public void setup() {
    builder = Json.createObjectBuilder();
  }

  @Test
  public void testCarParseComplete() {
    JsonObjectBuilder carBuilder = Json.createObjectBuilder();
    carBuilder.add(EntityStringNames.CAR_PRICE, 123.456);
    carBuilder.add(EntityStringNames.CAR_YEAR, 2021);
    carBuilder.add(EntityStringNames.CAR_MAKE, "Honda");
    carBuilder.add(EntityStringNames.CAR_MODEL, "Civic");
    builder.add(EntityStringNames.CAR_STRING, carBuilder);
    Parser parser = new JsonParser(builder.build());
    ParseCarUseCase parseCarUseCase;
    Car parsed = null;
    try {
      parseCarUseCase = new ParseCarUseCase(parser);
      parsed = parseCarUseCase.parse();
    } catch (Exceptions.ParseException e) {
      fail();
    }
    Car car = new Car(123.456, "Honda", "Civic", 2021);
    assertEquals(car.getPrice(), parsed.getPrice());
    assertEquals(car.getMake(), parsed.getMake());
    assertEquals(car.getModel(), parsed.getModel());
    assertEquals(car.getYear(), parsed.getYear());
  }

  @Test
  public void testCarParseBadTypes() {
    JsonObjectBuilder carBuilder = Json.createObjectBuilder();
    carBuilder.add(EntityStringNames.CAR_PRICE, "oops! i'm not a double!");
    carBuilder.add(EntityStringNames.CAR_YEAR, 2021);
    carBuilder.add(EntityStringNames.CAR_MAKE, 15);
    carBuilder.add(EntityStringNames.CAR_MODEL, 36.4);
    builder.add(EntityStringNames.CAR_STRING, carBuilder);
    Parser parser = new JsonParser(builder.build());
    ParseCarUseCase parseCarUseCase = null;
    try {
      parseCarUseCase = new ParseCarUseCase(parser);
    } catch (Exceptions.ParseException e) {
      fail(); // This part shouldn't be throwing an exception
    }
    try {
      parseCarUseCase.parse();
    } catch (Exceptions.ParseException e) {

    }
  }

  @Test
  public void testBuyerParseWrongNames() {
    JsonObjectBuilder carBuilder = Json.createObjectBuilder();
    carBuilder.add("not the correct string for car price", 15);
    carBuilder.add("wrong string for car year", 38);
    // no values for make or model either!!
    builder.add(EntityStringNames.CAR_STRING, carBuilder);
    Parser parser = new JsonParser(builder.build());
    ParseCarUseCase parseCarUseCase = null;
    try {
      parseCarUseCase = new ParseCarUseCase(parser);
    } catch (Exceptions.ParseException e) {
      fail(); // this part shouldn't be throwing an exception
    }
    try {
      parseCarUseCase.parse();
    } catch (Exceptions.ParseException e) {
      return;
    }
    fail(); // exception should definitely be thrown as input values were null
  }
}
