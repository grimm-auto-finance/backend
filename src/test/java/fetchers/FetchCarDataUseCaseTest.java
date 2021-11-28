package fetchers;

import constants.Exceptions;
import entities.Car;
import entities.TestEntityCreator;
import entitypackagers.AttributizeCarUseCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FetchCarDataUseCaseTest {

    @Test
    public void testGetCar() {
        int id = 5;
        Car testCar = TestEntityCreator.getTestCar(id);
        for (int i = 0; i < 3; i++) {
            testCar.addAddOn(TestEntityCreator.getTestAddOn());
        }
        FakeDataBaseFetcher fetcher = new FakeDataBaseFetcher();
        FetchCarDataUseCase fetchCarDataUseCase = new FetchCarDataUseCase(fetcher);
        try {
            assertEquals(testCar, fetchCarDataUseCase.getCar(id));
        } catch (Exceptions.CodedException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testSearch() {
        List<Car> testCarList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            testCarList.add(TestEntityCreator.getTestCar());
        }
        FakeDataBaseFetcher fetcher = new FakeDataBaseFetcher();
        FetchCarDataUseCase fetchCarDataUseCase = new FetchCarDataUseCase(fetcher);
        try {
            assertEquals(testCarList, fetchCarDataUseCase.search("test search"));
        } catch (Exceptions.CodedException e) {
            fail(e.getMessage());
        }
    }
}
