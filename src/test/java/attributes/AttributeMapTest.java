package attributes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AttributeMapTest {

    @Test
    public void TestaddItemAttributeAndGet(){
        AttributeMap attMap = new AttributeMap();
        try {
            attMap.addItem("integer_Attribute", new IntAttribute(1));
        } catch(ClassCastException e){
            fail();
        }

        assertEquals(new IntAttribute(1).getAttribute(),
                attMap.getItem("integer_Attribute").getAttribute());

    }

    @Test
    public void TestaddItemObjectAndGet(){
        AttributeMap attMap = new AttributeMap();
        try {
            attMap.addItem("integer_Object", 1);
        } catch(ClassCastException e){
            fail();
        }

        assertEquals(new IntAttribute(1).getAttribute(),
                attMap.getItem("integer_Object").getAttribute());

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
        assertEquals( "{double_Attribute=1.1, integer_Attribute=1}", attMap.getAttribute().toString());

    }

}