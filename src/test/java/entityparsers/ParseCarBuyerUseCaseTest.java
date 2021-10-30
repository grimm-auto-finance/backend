package entityparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.CarBuyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ParseCarBuyerUseCaseTest {

    static JsonObjectBuilder builder;

    @BeforeEach
    public void setup() {
        builder = Json.createObjectBuilder();
    }


    @Test
    public void testCarParseComplete() {
        JsonObjectBuilder buyerBuilder = Json.createObjectBuilder();
        buyerBuilder.add(EntityStringNames.BUYER_BUDGET, 123.456);
        buyerBuilder.add(EntityStringNames.BUYER_CREDIT, 750);
        builder.add(EntityStringNames.BUYER_STRING, buyerBuilder);
        Parser parser = new JsonParser(builder.build());
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
