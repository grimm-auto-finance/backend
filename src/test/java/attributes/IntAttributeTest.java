package attributes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntAttributeTest {
    @Test
    public void testGetAttribute() {
        IntAttribute intAtt = new IntAttribute(1);
        assertEquals(1, intAtt.getAttribute());
    }
}
