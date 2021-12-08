package attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class AttributeMapTest {

    static AttributeMap attMap;
    static IntAttribute intAtt = new IntAttribute(1);
    static DoubleAttribute doubleAtt = new DoubleAttribute(1.1);

    @BeforeEach
    public void setup() {
        attMap = new AttributeMap();
    }

    @Test
    public void testAddAttributeItem() {
        try {
            attMap.addItem("integer attribute", intAtt);
        } catch (ClassCastException e) {
            fail();
        }

        assertEquals(intAtt, attMap.getItem("integer attribute"));
    }

    @Test
    public void testAddObjectItem() {
        try {
            attMap.addItem("integer object", 1);
        } catch (ClassCastException e) {
            fail();
        }

        assertEquals(intAtt.getAttribute(), attMap.getItem("integer object").getAttribute());
    }

    @Test
    public void testGetAttribute() {
        Map<String, Attribute> testMap = new HashMap<>();
        testMap.put("integer attribute", intAtt);
        testMap.put("double attribute", doubleAtt);
        try {
            attMap.addItem("integer attribute", intAtt);
            attMap.addItem("double attribute", doubleAtt);
        } catch (ClassCastException e) {
            fail();
        }
        assertEquals(testMap, attMap.getAttribute());
    }

    @Test
    public void testGetItemMissingItem() {
        try {
            attMap.getItem("this item doesn't exist!");
        } catch (NullPointerException e) {
            return;
        }
        fail();
    }

    @Test
    public void testAddUnhandledItem() {
        try {
            attMap.addItem("no corresponding Attribute", new Object());
        } catch (ClassCastException e) {
            return;
        }
        fail();
    }
}
