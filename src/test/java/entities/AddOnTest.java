package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddOnTest {

    static AddOn emptyAddOn;
    static AddOn fullAddOn;

    @BeforeEach
    public void setup() {
        emptyAddOn = new AddOn("Marshmallows");
        fullAddOn = new AddOn("Rust-proofing", 30000, "no rust allowed!");
    }

    @Test
    public void testGetPrice() {
        assertEquals(0.0, emptyAddOn.getPrice());
        assertEquals(30000, fullAddOn.getPrice());
    }

    @Test
    public void testGetName() {
        assertEquals("Marshmallows", emptyAddOn.getName());
        assertEquals("Rust-proofing", fullAddOn.getName());
    }

    @Test
    public void testGetDescription() {
        assertNull(emptyAddOn.getDescription());
        assertEquals("no rust allowed!", fullAddOn.getDescription());
    }

    @Test
    public void testSetPrice() {
        emptyAddOn.setPrice(5.59);
        assertEquals(5.59, emptyAddOn.getPrice());
    }

    @Test
    public void testSetDescription() {
        emptyAddOn.setDescription("sweet yummy goodness");
        assertEquals("sweet yummy goodness", emptyAddOn.getDescription());
    }
}
