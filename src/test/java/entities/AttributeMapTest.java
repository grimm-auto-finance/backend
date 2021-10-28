package entities;

import attributes.AttributeMap;
import attributes.DoubleAttribute;
import attributes.IntAttribute;
import attributes.StringAttribute;

import org.junit.jupiter.api.Test;

public class AttributeMapTest {

    @Test
    public void testAttributeMap() {
        AttributeMap test = new AttributeMap();
        test.addItem("Bruh", new IntAttribute(5));
        test.addItem("String", new StringAttribute("hello"));
        test.addItem("double", new DoubleAttribute(56.5));
        AttributeMap subMap = new AttributeMap();
        subMap.addItem("sub-string", new StringAttribute("substring!"));
        test.addItem("submap", subMap);
        System.out.println(test);
    }
}
