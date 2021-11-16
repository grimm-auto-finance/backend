package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import attributes.AttributeFactory;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CarFactoryTest {

    @Test
    public void testGetEntityWorkingNoAddOns() {
        AttributeMap carMap = new AttributeMap();
        carMap.addItem(EntityStringNames.CAR_MAKE, "Honda");
        carMap.addItem(EntityStringNames.CAR_MODEL, "Civic");
        carMap.addItem(EntityStringNames.CAR_PRICE, 3000.0);
        // TODO: update this to be an int once we have parsing ints/doubles figured out
        carMap.addItem(EntityStringNames.CAR_YEAR, 2020.0);
        AttributeMap addOnMap = new AttributeMap();
        carMap.addItem(EntityStringNames.ADD_ON_STRING, addOnMap);
        carMap.addItem(EntityStringNames.CAR_KILOMETRES, 100.0);
        Car testCar = new Car(100.0, 3000.0, "Honda", "Civic", 2020);
        try {
            assertEquals(testCar, CarFactory.getEntity(carMap));
        } catch (Exceptions.FactoryException e) {
            fail();
        }
    }

    @Test
    public void testGetEntityWorkingWithAddOns() {
        AttributeMap carMap = new AttributeMap();
        carMap.addItem(EntityStringNames.CAR_MAKE, "Honda");
        carMap.addItem(EntityStringNames.CAR_MODEL, "Civic");
        carMap.addItem(EntityStringNames.CAR_PRICE, 3000.0);
        // TODO: update this to be an int once we have parsing ints/doubles figured out
        carMap.addItem(EntityStringNames.CAR_YEAR, 2020.0);
        AttributeMap addOnMap = new AttributeMap();
        addOnMap.addItem(EntityStringNames.ADD_ON_NAME, "rust-proofing");
        addOnMap.addItem(EntityStringNames.ADD_ON_PRICE, 15.25);
        addOnMap.addItem(EntityStringNames.ADD_ON_DESCRIPTION, "no rust allowed!");
        AttributeMap addOns = new AttributeMap();
        addOns.addItem("rust-proofing", addOnMap);
        carMap.addItem(EntityStringNames.ADD_ON_STRING, addOns);

        AddOn addOn = new AddOn("rust-proofing", 15.25, "no rust allowed!");
        Map<String, AddOn> addOnHashMap = new HashMap<>();
        addOnHashMap.put("rust-proofing", addOn);
        carMap.addItem(EntityStringNames.CAR_KILOMETRES, 100.0);
        Car testCar = new Car(100.0, 3000.0, "Honda", "Civic", 2020, addOnHashMap);

        try {
            assertEquals(testCar, CarFactory.getEntity(carMap));
        } catch (Exceptions.FactoryException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetEntityMissingKeys() {
        AttributeMap carMap = new AttributeMap();
        carMap.addItem("wrong key", "not gonna work");
        try {
            CarFactory.getEntity(carMap);
        } catch (Exceptions.FactoryException e) {
            return;
        }
        fail();
    }

    @Test
    public void testGetEntityWrongValueTypes() {
        AttributeMap carMap = new AttributeMap();
        carMap.addItem(EntityStringNames.CAR_MAKE, 15);
        carMap.addItem(EntityStringNames.CAR_MODEL, 36.5);
        carMap.addItem(EntityStringNames.CAR_PRICE, "whoops");
        carMap.addItem(EntityStringNames.CAR_YEAR, "uh oh");
        carMap.addItem(EntityStringNames.ADD_ON_STRING, AttributeFactory.createAttribute("yikes"));
        carMap.addItem(EntityStringNames.CAR_KILOMETRES, "yippee");
        try {
            CarFactory.getEntity(carMap);
        } catch (Exceptions.FactoryException e) {
            return;
        }
        fail();
    }
}
