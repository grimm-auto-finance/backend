package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

class ChildCar extends Car {
    public ChildCar(Double price, String make, String model, int year) {
        super(price, make, model, year);
    }
}

public class CarTest {
    @Test
    public void gettersAndSetters() {
        Car car = new Car(10000.0, "Honda", "Civic", 2002);
        assertEquals(10000, car.getPrice());
        assertEquals("Honda", car.getMake());
        assertEquals("Civic", car.getModel());
        assertEquals(2002, car.getYear());
        assertEquals(car.getAddOns(), new HashMap<>());
        car.setPrice(11000.0);
        assertEquals(11000, car.getPrice());
    }

    @Test
    public void addOns() {
        Car car = new Car(10000.0, "Honda", "Civic", 2002);
        AddOn marshmallows = new AddOn("Marshmallows", 5.59, "Very Tasty");
        car.addAddOn(marshmallows);
        HashMap<String, AddOn> hashMap = new HashMap<>();
        hashMap.put(marshmallows.getName(), marshmallows);
        assertEquals(car.getAddOns(), hashMap);
        car.removeAddOn(marshmallows);
        assertEquals(car.getAddOns(), new HashMap<>());
    }
}
