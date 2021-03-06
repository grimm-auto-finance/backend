package parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;

import constants.Exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class JsonParserTest {

    static JsonObjectBuilder builder;
    static JsonParser parser;
    static AttributeMap testMap;

    @BeforeEach
    public void setup() {
        builder = Json.createObjectBuilder();
        testMap = new AttributeMap();
        parser = new JsonParser();
    }

    @Test
    public void testSetParseObjectInputStream() {
        builder.add("test string", "test");
        JsonObject testObj = builder.build();
        InputStream testIS = new ByteArrayInputStream(testObj.toString().getBytes());
        JsonParser testParser = new JsonParser();
        try {
            testParser.setParseObject(testObj);
            AttributeMap testResult = testParser.parse();
            parser = new JsonParser();
            parser.setParseObject(testIS);
            assertEquals(testResult.toString(), parser.parse().toString());
        } catch (Exceptions.ParseException e) {
            fail();
        }
    }

    /**
     * Adds the given String value to both builder and testMap with the given name
     *
     * @param name Name of string value to be added
     * @param value String value to be added
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

    static void setParseObj() {
        try {
            parser.setParseObject(builder.build());
        } catch (Exceptions.ParseException e) {
            fail();
        }
    }

    @Test
    public void testParserAllStrings() {
        addToBoth("String value", "test value");
        addToBoth("String 2", "string 2 value");
        setParseObj();
        testEq(parser);
    }

    @Test
    public void testParserAllInts() {
        addToBoth("int value", 16);
        addToBoth("int value 2", 18);
        setParseObj();
        testEq(parser);
    }

    @Test
    public void testParserAllDoubles() {
        addToBoth("double value", 18.0);
        addToBoth("double value 2", 20.56);
        setParseObj();
        testEq(parser);
    }

    @Test
    public void testParserArray() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder.add(5.0);
        arrayBuilder.add(10.5);
        arrayBuilder.add("Hello");
        AttributeMap subMap = new AttributeMap();
        subMap.addItem("sub item", "woah!");
        Attribute[] attArray = {
            AttributeFactory.createAttribute(5.0),
            AttributeFactory.createAttribute(10.5),
            AttributeFactory.createAttribute("Hello"),
            subMap
        };
        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        subBuilder.add("sub item", "woah!");
        arrayBuilder.add(subBuilder.build());
        builder.add("Array value", arrayBuilder.build());
        testMap.addItem("Array value", attArray);
        setParseObj();
        testEq(parser);
    }

    @Test
    public void testJsonParserNullValues() {
        builder.addNull("NULL VALUE!");
        setParseObj();
        try {
            parser.parse();
        } catch (Exceptions.ParseException e) {
            return;
        }
        fail();
    }

    @Test
    public void testJsonParserManyValues() {
        addToBoth("Int value", 5);
        addToBoth("double value .0", 10.0);
        addToBoth("String value", "hello");
        addToBoth("Double value", 5.5);
        AttributeMap subMap = new AttributeMap();
        subMap.addItem("Sub-String value", "sub-string");
        testMap.addItem("Sub-object", subMap);
        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        subBuilder.add("Sub-String value", "sub-string");
        builder.add("Sub-object", subBuilder);
        setParseObj();
        testEq(parser);
    }
}
