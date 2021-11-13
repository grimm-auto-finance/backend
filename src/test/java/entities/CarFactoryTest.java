package entities;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CarFactoryTest {

    @Test
    public void testGetEntityWorkingNoAddOns() {
        AttributeMap carMap = new AttributeMap();
        carMap.addItem(EntityStringNames.CAR_MAKE, "Honda");
        carMap.addItem(EntityStringNames.CAR_MODEL, "Civic");
        carMap.addItem(EntityStringNames.CAR_PRICE, 3000.0);
        // TODO: update this to be an int once we have parsing ints/doubles figured out
        carMap.addItem(EntityStringNames.CAR_YEAR, 2020.0);
        ArrayAttribute addOnArray = (ArrayAttribute) AttributeFactory.createAttribute(new Attribute[0]);
        carMap.addItem(EntityStringNames.CAR_ADD_ONS, addOnArray);
        Car testCar = new Car(3000.0, "Honda", "Civic", 2020);
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
        Attribute[] addOns = {addOnMap};
        ArrayAttribute addOnArray = (ArrayAttribute) AttributeFactory.createAttribute(addOns);
        carMap.addItem(EntityStringNames.CAR_ADD_ONS, addOnArray);

        AddOn addOn = new AddOn("rust-proofing", 15.25, "no rust allowed!");
        Map<String, AddOn> addOnHashMap = new HashMap<>();
        addOnHashMap.put("rust-proofing", addOn);
        Car testCar = new Car(3000.0, "Honda", "Civic", 2020, addOnHashMap);

        try {
            assertEquals(testCar, CarFactory.getEntity(carMap));
        } catch (Exceptions.FactoryException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testCarFactoryMissingKeys() {
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
    public void testCarFactoryWrongValueTypes() {
        AttributeMap carMap = new AttributeMap();
        carMap.addItem(EntityStringNames.CAR_MAKE, 15);
        carMap.addItem(EntityStringNames.CAR_MODEL, 36.5);
        carMap.addItem(EntityStringNames.CAR_PRICE, "whoops");
        carMap.addItem(EntityStringNames.CAR_YEAR, "uh oh");
        carMap.addItem(EntityStringNames.CAR_ADD_ONS, AttributeFactory.createAttribute("yikes"));
        try {
            CarFactory.getEntity(carMap);
        } catch (Exceptions.FactoryException e) {
            return;
        }
        fail();
    }
}
