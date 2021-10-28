package entityparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import constants.EntityStringNames;
import constants.Exceptions;

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
        ParseCarBuyerUseCase parseCarBuyerUseCase;
        CarBuyer parsed = null;
        try {
            parseCarBuyerUseCase = new ParseCarBuyerUseCase(parser);
            parsed = parseCarBuyerUseCase.parse();
        } catch (Exceptions.ParseException e) {
            fail();
        }
        CarBuyer carBuyer = new CarBuyer(123.456, 750);
        assertEquals(carBuyer.getBudget(), parsed.getBudget());
        assertEquals(carBuyer.getCreditScore(), parsed.getCreditScore());
    }
}
