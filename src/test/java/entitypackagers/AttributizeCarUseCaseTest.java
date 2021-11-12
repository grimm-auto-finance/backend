package entitypackagers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.AddOn;
import entities.Car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttributizeCarUseCaseTest {

    static AttributeMap testMap;
    static Car testCar;
    static AttributizeCarUseCase carAttributizer;

    @BeforeEach
    public void setup() {
        testCar = new Car(0, 50000, "Honda", "Civic", 2020);
        carAttributizer = new AttributizeCarUseCase(testCar);
        testMap = new AttributeMap();
    }

    static void addCarToTestMap() {
        testMap.addItem(EntityStringNames.CAR_PRICE, testCar.getPrice());
        testMap.addItem(EntityStringNames.CAR_MAKE, testCar.getMake());
        testMap.addItem(EntityStringNames.CAR_MODEL, testCar.getModel());
        testMap.addItem(EntityStringNames.CAR_YEAR, testCar.getYear());
        testMap.addItem(
                EntityStringNames.CAR_ADD_ONS,
                AttributizeCarUseCase.getAddOnAttArray(testCar.getAddOns()));
        testMap.addItem(EntityStringNames.CAR_KILOMETRES, testCar.getKilometres());

    }

    @Test
    public void testAttributizeCarNoAddOns() {
        addCarToTestMap();
        assertEquals(
                testMap.getAttribute().toString(),
                carAttributizer.attributizeEntity().getAttribute().toString());
    }

    @Test
    public void testAttributizeCarWithAddOns() {
        AddOn rustProofing = new AddOn("Rust proofing", 1000, "no rust allowed");
        AddOn marshmallows = new AddOn("Marshmallows", 100, "fluffy goodness");
        testCar.addAddOn(rustProofing);
        testCar.addAddOn(marshmallows);
        addCarToTestMap();
        assertEquals(
                testMap.getAttribute().toString(),
                carAttributizer.attributizeEntity().getAttribute().toString());
    }
}
