package entitypackagers;

import static org.junit.jupiter.api.Assertions.*;

import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.Car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributizeCarIDUseCaseTest {
    static Car testCar;
    static AttributizeCarUseCase carAttributizer;

    @BeforeEach
    public void setup() {
        testCar = TestEntityCreator.getTestCar();
        carAttributizer = new AttributizeCarUseCase(testCar);
    }

    @Test
    public void testAttributizeCarAndId() {
        Object[] carAndIdArray = new Object[2];
        carAndIdArray[0] = 1;
        carAndIdArray[1] = testCar;
        AttributizeCarIDUseCase carAndId =
                new AttributizeCarIDUseCase(
                        extractIdFromIdArray(carAndIdArray), extractCarFromIdArray(carAndIdArray));
        AttributeMap carAndIdMap = carAndId.attributizeCarAndId();
        assertEquals(carAndIdMap.getItem(EntityStringNames.ID_STRING).getAttribute(), 1);
        assertEquals(
                carAndIdMap.getItem(EntityStringNames.CAR_STRING).getAttribute().toString(),
                carAttributizer.attributizeEntity().getAttribute().toString());
    }

    public static Car extractCarFromIdArray(Object[] idArray) {
        return (Car) idArray[1];
    }

    public static int extractIdFromIdArray(Object[] idArray) {
        return (int) idArray[0];
    }
}
