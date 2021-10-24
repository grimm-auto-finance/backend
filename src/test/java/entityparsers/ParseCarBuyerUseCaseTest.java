package entityparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import constants.EntityStringNames;
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
                                EntityStringNames.BUYER_STRING,
                                Json.createObjectBuilder()
                                        .add(EntityStringNames.BUYER_BUDGET, 123.456)
                                        .add(EntityStringNames.BUYER_CREDIT, 750)
                                        .build())
                        .build();
        Parser parser = new JsonParser(obj);
        ParseCarBuyerUseCase parseCarBuyerUseCase = new ParseCarBuyerUseCase(parser);
        CarBuyer parsed = parseCarBuyerUseCase.parse();
        CarBuyer carBuyer = new CarBuyer(123.456, 750);
        assertEquals(carBuyer.getBudget(), parsed.getBudget());
        assertEquals(carBuyer.getCreditScore(), parsed.getCreditScore());
    }
}
