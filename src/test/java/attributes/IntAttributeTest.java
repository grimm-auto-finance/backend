package attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IntAttributeTest {
    @Test
    public void testGetAttribute() {
        IntAttribute intAtt = new IntAttribute(1);
        assertEquals(1, intAtt.getAttribute());
    }
}
