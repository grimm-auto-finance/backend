package entityparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import constants.EntityStringNames;
import constants.Exceptions;
import entities.Car;

import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;

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
}
