package entitypackagers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import attributes.*;

import constants.Exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

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
     * @param name Name of string value to be added
     * @param value String value to be added
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
    public void testPackagerArrays() {
        AttributeMap subMap = new AttributeMap();
        subMap.addItem("sub string", "sub-string");
        Attribute[] subAttArray = {AttributeFactory.createAttribute("sub array value")};
        Attribute[] attArray = {
            AttributeFactory.createAttribute(5),
            AttributeFactory.createAttribute(10.5),
            AttributeFactory.createAttribute("Hello"),
            subMap,
            AttributeFactory.createAttribute(subAttArray)
        };
        JsonObjectBuilder subBuilder = Json.createObjectBuilder();
        subBuilder.add("sub string", "sub-string");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder.add(5).add(10.5).add("Hello").add(subBuilder.build());
        JsonArrayBuilder subArrayBuilder = Json.createArrayBuilder();
        subArrayBuilder.add("sub array value");
        arrayBuilder.add(subArrayBuilder.build());
        map.addItem("Array!", attArray);
        testBuilder.add("Array!", arrayBuilder.build());
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

    @Test
    public void testJsonPackagerUnhandledAttributeInMap() {
        map.addItem("fake attribute item", new FakeAttribute(5));
        JsonPackager jsonPackager = new JsonPackager();
        try {
            // Throws a PackageException because FakeAttribute doesn't have handling code in
            // writePackage()
            jsonPackager.writePackage(map);
        } catch (Exceptions.PackageException e) {
            return;
        }
        fail();
    }

    @Test
    public void testJsonpackagerUnhandledAttribute() {
        JsonPackager jsonPackager = new JsonPackager();
        try {
            jsonPackager.writePackage(new FakeAttribute(5));
        } catch (Exceptions.PackageException e) {
            return;
        }
        fail();
    }

    @Test
    public void testJsonPackagerSingleInt() {
        JsonPackager jsonPackager = new JsonPackager();
        JsonValue intVal = Json.createValue(5);
        try {
            assertEquals(intVal, jsonPackager.writePackage(new IntAttribute(5)).getPackage());
        } catch (Exceptions.PackageException e) {
            fail();
        }
    }

    @Test
    public void testJsonPackagerSingleString() {
        JsonPackager jsonPackager = new JsonPackager();
        JsonValue stringVal = Json.createValue("test");
        try {
            assertEquals(
                    stringVal, jsonPackager.writePackage(new StringAttribute("test")).getPackage());
        } catch (Exceptions.PackageException e) {
            fail();
        }
    }

    @Test
    public void testJsonPackagerSingleDouble() {
        JsonPackager jsonPackager = new JsonPackager();
        JsonValue doubVal = Json.createValue(5.5);
        try {
            assertEquals(doubVal, jsonPackager.writePackage(new DoubleAttribute(5.5)).getPackage());
        } catch (Exceptions.PackageException e) {
            fail();
        }
    }
}
