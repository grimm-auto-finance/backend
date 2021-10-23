package entities;

import attributes.AttributeDouble;
import attributes.AttributeInt;
import attributes.AttributeMap;
import attributes.AttributeString;
import org.junit.jupiter.api.Test;

public class AttributeMapTest {

    @Test
    public void testAttributeMap() {
        AttributeMap test = new AttributeMap();
        test.addItem("Bruh", new AttributeInt(5));
        test.addItem("String", new AttributeString("hello"));
        test.addItem("double", new AttributeDouble(56.5));
        AttributeMap subMap = new AttributeMap();
        subMap.addItem("sub-string", new AttributeString("substring!"));
        test.addItem("submap", subMap);
        System.out.println(test);
    }
}
