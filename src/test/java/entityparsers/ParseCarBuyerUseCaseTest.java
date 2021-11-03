package entityparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.CarBuyer;

import org.junit.jupiter.api.Assertions;
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

    /** Test ParseCarBuyerUseCase in a "working" situation, with
     * all values present in the JSONObject and with the correct types.
     */
    @Test
    public void testBuyerParseWorking() {
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

    /** Test that ParseBuyerUseCase correctly throws a ParseException when
     * the types of objects in the JSONObject don't match the needed types
     * for creating a CarBuyer
     */
    @Test
    public void testBuyerParseBadTypes() {
        JsonObjectBuilder buyerBuilder = Json.createObjectBuilder();
        buyerBuilder.add(EntityStringNames.BUYER_BUDGET, "oops! i'm not a double!");
        buyerBuilder.add(EntityStringNames.BUYER_CREDIT, 750);
        builder.add(EntityStringNames.BUYER_STRING, buyerBuilder);
        Parser parser = new JsonParser(builder.build());
        ParseCarBuyerUseCase parseCarBuyerUseCase = null;
        try {
            parseCarBuyerUseCase = new ParseCarBuyerUseCase(parser);
        } catch (Exceptions.ParseException e) {
            fail(); // This part shouldn't be throwing an exception
        }
        try {
            parseCarBuyerUseCase.parse();
        } catch (Exceptions.ParseException e) {

        }
    }

    /** Test that ParseCarBuyerUseCase correctly throws a ParseException
     * when the JSONObject attribute names needed for creating a CarBuyer
     * either don't match or don't exist in the JsonObject
     */
    @Test
    public void testBuyerParseWrongNames() {
        JsonObjectBuilder buyerBuilder = Json.createObjectBuilder();
        buyerBuilder.add("not the correct string for buyer budget", 15);
        buyerBuilder.add("wrong string for buyer credit", 38);
        builder.add(EntityStringNames.BUYER_STRING, buyerBuilder);
        Parser parser = new JsonParser(builder.build());
        ParseCarBuyerUseCase parseCarBuyerUseCase = null;
        try {
            parseCarBuyerUseCase = new ParseCarBuyerUseCase(parser);
        } catch (Exceptions.ParseException e) {
            fail(); // this part shouldn't be throwing an exception
        }
        try {
            parseCarBuyerUseCase.parse();
        } catch (Exceptions.ParseException e) {
            return;
        }
        fail(); // exception should definitely be thrown as input values were missing
    }
}
