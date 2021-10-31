package attributes;
import constants.Exceptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static attributes.AttributeFactory.getAttribute;
import static org.junit.jupiter.api.Assertions.*;

public class AttributeFactoryTest {
    String[] cars = {"Audi"};
    Object[] obj = {1, 1.1, "Hello", cars};
    @Test
    public void testAttributeFactory() {

        IntAttribute intAt = new IntAttribute(1);
        DoubleAttribute doubAt = new DoubleAttribute((1.1));
        StringAttribute strAt = new StringAttribute("Hello");

        try {
            getAttribute(obj[3]);

        } catch (ClassCastException classCastException) {
        }


        assertEquals(1, getAttribute(obj[0]).getAttribute());
        assertEquals(1.1, getAttribute(obj[1]).getAttribute());
        assertEquals("Hello", getAttribute(obj[2]).getAttribute());

    }
}