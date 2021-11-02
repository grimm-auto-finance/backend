package attributes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringAttributeTest {
    @Test
    public void testGetAttribute() {
        StringAttribute strAtt = new StringAttribute("Hello");
        assertEquals("Hello", strAtt.getAttribute());
    }
}
