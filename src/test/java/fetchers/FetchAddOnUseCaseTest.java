package fetchers;

import constants.Exceptions;
import entities.AddOn;
import entities.TestEntityCreator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FetchAddOnUseCaseTest {

    @Test
    public void testGetAddOns() {
        List<AddOn> testAddOns = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            testAddOns.add(TestEntityCreator.getTestAddOn());
        }
        FakeDataBaseFetcher fakeDb = new FakeDataBaseFetcher();
        FetchAddOnDataUseCase addOnFetcher = new FetchAddOnDataUseCase(fakeDb);
        try {
            assertEquals(testAddOns, addOnFetcher.getAddOns(5));
        } catch (Exceptions.CodedException e) {
            fail(e.getMessage());
        }
    }
}
