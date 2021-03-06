package attributizing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.AddOn;
import entities.Car;
import entities.TestEntityCreator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttributizeCarUseCaseTest {

    static AttributeMap testMap;
    static Car testCar;
    static AttributizeCarUseCase carAttributizer;

    @BeforeEach
    public void setup() {
        testCar = TestEntityCreator.getTestCar();
        carAttributizer = new AttributizeCarUseCase(testCar);
        testMap = new AttributeMap();
    }

    static void addCarToTestMap() {
        testMap.addItem(EntityStringNames.CAR_PRICE, testCar.getPrice());
        testMap.addItem(EntityStringNames.CAR_MAKE, testCar.getMake());
        testMap.addItem(EntityStringNames.CAR_MODEL, testCar.getModel());
        testMap.addItem(EntityStringNames.CAR_IMAGE, testCar.getImage());
        testMap.addItem(EntityStringNames.CAR_YEAR, testCar.getYear());
        testMap.addItem(EntityStringNames.CAR_KILOMETRES, testCar.getKilometres());
        testMap.addItem(EntityStringNames.CAR_ID, testCar.getId());
        testMap.addItem(
                EntityStringNames.ADD_ON_STRING,
                AttributizeCarUseCase.getAddOnMap(testCar.getAddOns()));
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
        AddOn rustProofing = TestEntityCreator.getTestAddOn();
        testCar.addAddOn(rustProofing);
        addCarToTestMap();
        assertEquals(
                testMap.getAttribute().toString(),
                carAttributizer.attributizeEntity().getAttribute().toString());
    }
}
