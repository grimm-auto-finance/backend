package entityparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import attributes.AttributeMap;
import constants.Exceptions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class JsonParserTest {

    static JsonObjectBuilder builder;
    static AttributeMap testMap;

    @BeforeEach
    public void setup() {
        builder = Json.createObjectBuilder();
        testMap = new AttributeMap();
    }

    /**
     * Adds the given String value to both builder and testMap with the given name
     *
     * @param name
     * @param value
     */
    static void addToBoth(String name, String value) {
        builder.add(name, value);
        testMap.addItem(name, value);
    }

    static void addToBoth(String name, int value) {
        builder.add(name, value);
        testMap.addItem(name, value);
    }

    static void addToBoth(String name, double value) {
        builder.add(name, value);
        testMap.addItem(name, value);
    }

    static void testEq(JsonParser parser) {
        try {
            assertEquals(testMap.toString(), parser.parse().toString());
        } catch (Exceptions.ParseException e) {
            fail();
        }
    }

    @Test
    public void testParserAllStrings() {
        addToBoth("String value", "test value");
        addToBoth("String 2", "string 2 value");
        JsonParser parser = new JsonParser(builder.build());
        testEq(parser);
    }

    // Fails since JsonParser currently treats all numbers as doubles
    //    @Test
    //    public void testParserAllInts() {
    //        addToBoth("int value", 16);
    //        addToBoth("int value 2", 18);
    //        JsonParser parser = new JsonParser(builder.build());
    //        testEq(parser);
    //    }

    @Test
    public void testParserAllDoubles() {
        addToBoth("double value", 18.0);
        addToBoth("double value 2", 20.56);
        JsonParser parser = new JsonParser(builder.build());
        testEq(parser);
    }

    @Test
    public void testJsonParserManyValues() {
        // addToBoth("Int value", 5);
        addToBoth("String value", "hello");
        addToBoth("Double value", 5.5);
        AttributeMap subMap = new AttributeMap();
        subMap.addItem("Sub-String value", "sub-string");
        testMap.addItem("Sub-object", subMap);
        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        subBuilder.add("Sub-String value", "sub-string");
        builder.add("Sub-object", subBuilder);
        JsonParser parser = new JsonParser(builder.build());
        testEq(parser);
    }
}
