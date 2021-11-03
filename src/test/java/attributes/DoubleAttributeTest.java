package attributes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoubleAttributeTest {
  @Test
  public void testGetAttribute() {
    DoubleAttribute doubAtt = new DoubleAttribute(1.1);
    assertEquals(1.1, doubAtt.getAttribute());
  }
}
