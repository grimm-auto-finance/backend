package attributes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleAttributeTest {
    @Test
    public void TestGetAttribute() {
        DoubleAttribute doubAtt = new DoubleAttribute(1.1);
        assertEquals(1.1, doubAtt.getAttribute());
    }
}
