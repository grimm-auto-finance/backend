package entitypackagers;

import attributes.Attribute;
import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.AddOn;
import entities.Car;
import entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttributizeCarUseCaseTest {

    static AttributeMap testMap;
    static Car testCar;
    static AttributizeCarUseCase carAttributizer;

    @BeforeEach
    public void setup() {
        testCar = new Car(50000, "Honda", "Civic", 2020);
        carAttributizer = new AttributizeCarUseCase(testCar);
        testMap = new AttributeMap();
        addCarToTestMap();
    }

    static void addCarToTestMap() {
        testMap.addItem(EntityStringNames.CAR_PRICE, testCar.getPrice());
        testMap.addItem(EntityStringNames.CAR_MAKE, testCar.getMake());
        testMap.addItem(EntityStringNames.CAR_MODEL, testCar.getModel());
        testMap.addItem(EntityStringNames.CAR_YEAR, testCar.getYear());
    }

    @Test
    public void testAttributizeCarNoAddOns() {
        testMap.addItem(EntityStringNames.CAR_ADD_ONS, new AttributeMap());
        assertEquals(testMap.getAttribute().toString(), carAttributizer.attributizeEntity().getAttribute().toString());
    }

    @Test
    public void testAttributizeCarWithAddOns() {
        AddOn rustProofing = new AddOn("Rust proofing", 1000, "no rust allowed");
        //testCar.addAddOn(marshmallows);
        testCar.addAddOn(rustProofing);
        AttributeMap addOnMap = new AttributeMap();
        // TODO: uncomment this part of test when AddOn constructor situation has been figured out
        //AttributizeAddOnUseCase addOnAttributizer = new AttributizeAddOnUseCase(marshmallows);
        //addOnMap.addItem("Marshmallows " + EntityStringNames.ADD_ON_STRING, (Attribute) addOnAttributizer.attributizeEntity());
        AttributizeAddOnUseCase addOnAttributizer = new AttributizeAddOnUseCase(rustProofing);
        addOnMap.addItem("Rust proofing " + EntityStringNames.ADD_ON_STRING, (Attribute) addOnAttributizer.attributizeEntity());
        testMap.addItem(EntityStringNames.CAR_ADD_ONS, addOnMap);
        assertEquals(testMap.getAttribute().toString(), carAttributizer.attributizeEntity().getAttribute().toString());
    }

}