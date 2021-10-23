package entityparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entities.CarBuyer;

import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;

public class ParseCarBuyerUseCaseTest {
    @Test
    void parse() {
        JsonObject obj =
                Json.createObjectBuilder()
                        .add(
                                "car buyer",
                                Json.createObjectBuilder()
                                        .add("pytBudget", 123.456)
                                        .add("creditScore", 750)
                                        .build())
                        .build();
        ParseCarBuyerUseCase parseCarBuyerUseCase = new ParseCarBuyerUseCase(obj);
        CarBuyer parsed = parseCarBuyerUseCase.parse();
        CarBuyer carBuyer = new CarBuyer(123.456, 750);
        assertEquals(carBuyer.getBudget(), parsed.getBudget());
        assertEquals(carBuyer.getCreditScore(), parsed.getCreditScore());
    }
}
