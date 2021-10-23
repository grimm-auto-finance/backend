package entityparsers;

import entities.Car;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParseCarUseCaseTest {
    @Test
    void parse() {
        JsonObject obj = Json.createObjectBuilder()
                .add("car", Json.createObjectBuilder()
                        .add("vehiclePrice", 10000)
                        .add("vehicleMake", "Honda")
                        .add("vehicleModel", "Civic")
                        .add("vehicleYear", 2002).build()).build();
        ParseCarUseCase parseCarUseCase = new ParseCarUseCase(obj);
        Car parsed = parseCarUseCase.parse();
        Car car = new Car(10000, "Honda", "Civic", 2002);
        assertEquals(car.getAddOns(), parsed.getAddOns());
        assertEquals(car.getMake(), parsed.getMake());
        assertEquals(car.getModel(), parsed.getModel());
        assertEquals(car.getPrice(), parsed.getPrice());
        assertEquals(car.getYear(), parsed.getYear());
    }
}
