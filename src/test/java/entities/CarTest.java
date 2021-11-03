package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import constants.EntityStringNames;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CarTest {

    static Car car;
    static Map<String, AddOn> addOns;

    @BeforeEach
    public void setup() {
        car = new Car(30000, "Honda", "Civic", 2002);
        addOns = new HashMap<>();
        addOns.put("Marshmallows", new AddOn("Marshmallows"));
        addOns.put("Rust proofing", new AddOn("Rust proofing", 1000, "no rust allowed!"));
    }

    @Test
    public void testGetPrice() {
        assertEquals(30000, car.getPrice());
    }

    @Test
    public void testGetMake() {
        assertEquals("Honda", car.getMake());
    }

    @Test
    public void testGetModel() {
        assertEquals("Civic", car.getModel());
    }

    @Test
    public void testGetYear() {
        assertEquals(2002, car.getYear());
    }

    @Test
    public void testEmptyAddOnConstructor() {
        assertEquals(new HashMap<>(), car.getAddOns());
    }

    @Test
    public void testAddOnConstructor() {
        car = new Car(30000, "Honda", "Civic", 2002, addOns);
        assertEquals(addOns, car.getAddOns());
    }

    @Test
    public void testSetPrice() {
        car.setPrice(50000);
        assertEquals(50000, car.getPrice());
    }

    @Test
    public void testAddAddOns() {
        car.addAddOn(addOns.get("Marshmallows"));
        car.addAddOn(addOns.get("Rust proofing"));
        assertEquals(addOns, car.getAddOns());
    }

    @Test
    public void testRemoveAddOns() {
        car = new Car(30000, "Honda", "Civic", 2002, addOns);
        car.removeAddOn("Marshmallows");
        assertFalse(addOns.containsKey("Marshmallows"));
    }

    @Test
    public void testGetStringName() {
        assertEquals(EntityStringNames.CAR_STRING, car.getStringName());
    }
}
