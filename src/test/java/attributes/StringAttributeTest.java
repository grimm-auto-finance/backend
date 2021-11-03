package attributes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StringAttributeTest {
    @Test
    public void testGetAttribute() {
        StringAttribute strAtt = new StringAttribute("Hello");
        assertEquals("Hello", strAtt.getAttribute());
    }
}
