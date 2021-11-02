package entitypackagers;

import attributes.AttributeMap;
import constants.Exceptions;
import entityparsers.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonPackagerTest {

    static AttributeMap map;
    static JsonObjectBuilder testBuilder;

    @BeforeEach
    public void setup() {
        map = new AttributeMap();
        testBuilder = Json.createObjectBuilder();
    }

    /**
     * Adds the given String value to both builder and testMap with the given name
     *
     * @param name
     * @param value
     */
    static void addToBoth(String name, String value) {
        testBuilder.add(name, value);
        map.addItem(name, value);
    }

    static void addToBoth(String name, int value) {
        testBuilder.add(name, value);
        map.addItem(name, value);
    }

    static void addToBoth(String name, double value) {
        testBuilder.add(name, value);
        map.addItem(name, value);
    }

    static void testEq(JsonPackager packager) {
        try {
            assertEquals(testBuilder.build(), packager.writePackage(map).getPackage());
        } catch (Exceptions.PackageException e) {
            fail();
        }
    }

    @Test
    public void testPackagerAllStrings() {
        addToBoth("String value", "test value");
        addToBoth("String 2", "string 2 value");
        JsonPackager packager = new JsonPackager();
        testEq(packager);
    }

    @Test
    public void testPackagerAllInts() {
        addToBoth("int value", 16);
        addToBoth("int value 2", 18);
        JsonPackager packager = new JsonPackager();
        testEq(packager);
    }

    @Test
    public void testPackagerAllDoubles() {
        addToBoth("double value", 18.0);
        addToBoth("double value 2", 20.56);
        JsonPackager packager = new JsonPackager();
        testEq(packager);
    }

    @Test
    public void testPackagerSubMaps() {
        AttributeMap subMap = new AttributeMap();
        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        subMap.addItem("sub string", "sub-string");
        subBuilder.add("sub string", "sub-string");
        subMap.addItem("sub int", 5);
        subBuilder.add("sub int", 5);
        subMap.addItem("sub double", 16.5);
        subBuilder.add("sub double", 16.5);
        map.addItem("sub map", subMap);
        testBuilder.add("sub map", subBuilder);
        JsonPackager packager = new JsonPackager();
        testEq(packager);
    }

    @Test
    public void testJsonPackagerMixedValues() {
        addToBoth("Int value", 5);
        addToBoth("String value", "hello");
        addToBoth("Double value", 5.5);
        AttributeMap subMap = new AttributeMap();
        subMap.addItem("Sub-String value", "sub-string");
        map.addItem("Sub-object", subMap);
        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        subBuilder.add("Sub-String value", "sub-string");
        testBuilder.add("Sub-object", subBuilder);
        JsonPackager packager = new JsonPackager();
        testEq(packager);
    }

}
