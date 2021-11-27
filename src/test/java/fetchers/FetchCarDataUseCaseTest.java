package fetchers;

import constants.Exceptions;
import entities.Car;
import entities.TestEntityCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FetchCarDataUseCaseTest {

    @Test
    public void testGetCar() {
        int id = 5;
        Car testCar = TestEntityCreator.getTestCar(id);
        FakeDataBaseFetcher fetcher = new FakeDataBaseFetcher();
        FetchCarDataUseCase fetchCarDataUseCase = new FetchCarDataUseCase(fetcher);
        try {
            assertEquals(testCar, fetchCarDataUseCase.getCar(id));
        } catch (Exceptions.CodedException e) {
            fail(e.getMessage());
        }
    }
}
