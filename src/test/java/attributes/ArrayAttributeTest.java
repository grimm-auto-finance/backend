package attributes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayAttributeTest {

    static Attribute[] attributes;
    static AttributeMap map;

    @BeforeAll
    static void setup() {
        DoubleAttribute doub = new DoubleAttribute(5.25);
        IntAttribute num = new IntAttribute(5);
        StringAttribute str = new StringAttribute("string attribute");
        map = new AttributeMap();
        map.addItem("test map attr 1", 16);
        map.addItem("test map attr 2", 2.35);
        map.addItem("test map attr 3", "sub attr");
        attributes = new Attribute[4];
        attributes[0] = doub;
        attributes[1] = num;
        attributes[2] = str;
        attributes[3] = map;
    }

    @Test
    public void testGetAttribute() {
        ArrayAttribute arrayAttribute = new ArrayAttribute(attributes);
        assertEquals(attributes, arrayAttribute.getAttribute());
    }

}
