package attributes;

import static attributes.AttributeFactory.createAttribute;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class AttributeFactoryTest {
    static Attribute testAtt;

    @Test
    public void testAttributeFactoryIntAttribute() {
        testAtt = createAttribute(1);
        assertTrue(testAtt instanceof IntAttribute);
        assertEquals(1, testAtt.getAttribute());
    }

    @Test
    public void testAttributeFactoryStringAttribute() {
        testAtt = createAttribute("test");
        assertTrue(testAtt instanceof StringAttribute);
        assertEquals("test", testAtt.getAttribute());
    }

    @Test
    public void testAttributeFactoryDoubleAttribute() {
        testAtt = createAttribute(1.1);
        assertTrue(testAtt instanceof DoubleAttribute);
        assertEquals(1.1, testAtt.getAttribute());
    }

    @Test
    public void testAttributeFactoryNoAttributeType() {
        try {
            testAtt = createAttribute(new Object());
        } catch (ClassCastException e) {
            return;
        }
        fail();
    }
}
