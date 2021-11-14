package attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DoubleAttributeTest {
    @Test
    public void testGetAttribute() {
        DoubleAttribute doubAtt = new DoubleAttribute(1.1);
        assertEquals(1.1, doubAtt.getAttribute());
    }
}
