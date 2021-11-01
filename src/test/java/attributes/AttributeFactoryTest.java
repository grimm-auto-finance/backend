package attributes;

import org.junit.jupiter.api.Test;

import static attributes.AttributeFactory.getAttribute;
import static org.junit.jupiter.api.Assertions.*;

public class AttributeFactoryTest {
    String[] cars = {"Audi"};
    Object[] obj = {1, 1.1, "Hello", cars};

    @Test
    public void testAttributeFactory() {

        assertEquals(1, getAttribute(obj[0]).getAttribute());
        assertEquals(1.1, getAttribute(obj[1]).getAttribute());
        assertEquals("Hello", getAttribute(obj[2]).getAttribute());
    }
}
