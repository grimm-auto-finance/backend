package attributes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AttributeMapTest {
    public IntAttribute intAt = new IntAttribute(1);
    DoubleAttribute doubAt = new DoubleAttribute((1.1));
    StringAttribute strAt = new StringAttribute("Hello");

    @Test
    public void TestaddItemAttributeAndGet(){
        AttributeMap attMap = new AttributeMap();
        try {
            attMap.addItem("integer_Attribute", new IntAttribute(1));
        } catch(ClassCastException e){
            fail();
        }

        assertEquals(attMap.getItem("integer_Attribute"), new IntAttribute(1));

    }

    @Test
    public void TestaddItemObjectAndGet(){
        AttributeMap attMap = new AttributeMap();
        try {
            attMap.addItem("integer_Object", (Object) 1);
        } catch(ClassCastException e){
            fail();
        }

        assertEquals(attMap.getItem("integer_Object"), new IntAttribute(1));

    }
    @Test
    public void TestGetAttribute(){
        AttributeMap attMap = new AttributeMap();
        try {
            attMap.addItem("integer_Attribute", new IntAttribute(1));
            attMap.addItem("double_Attribute",new DoubleAttribute(1.1));
        } catch(ClassCastException e){
            fail();
        }
        System.out.println(attMap);
        assertEquals(attMap.getAttribute().toString(), "{double_Attribute=1.1, integer_Attribute=1}");

    }

}