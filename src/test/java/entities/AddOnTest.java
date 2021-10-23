package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AddOnTest {
    @Test
    public void gettersAndSetters() {
        AddOn addOn = new AddOn("Marshmallows");
        assertEquals("Marshmallows", addOn.getName());
        assertEquals(0.0, addOn.getPrice());
        assertNull(addOn.getDescription());
        addOn.setPrice(5.59);
        addOn.setDescription("Very Tasty");
        assertEquals("Marshmallows", addOn.getName());
        assertEquals(5.59, addOn.getPrice());
        assertEquals("Very Tasty", addOn.getDescription());
    }
}