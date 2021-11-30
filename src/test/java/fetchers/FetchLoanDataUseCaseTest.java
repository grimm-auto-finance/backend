package fetchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import constants.Exceptions;

import entities.LoanData;
import entities.TestEntityCreator;

import entitypackagers.JsonPackager;

import org.junit.jupiter.api.Test;

public class FetchLoanDataUseCaseTest {

    @Test
    public void testFetch() {
        LoanData testLoan = TestEntityCreator.getTestLoanData();
        FakeHttpFetcher fetcher = new FakeHttpFetcher();
        FetchLoanDataUseCase loanFetcher =
                new FetchLoanDataUseCase(fetcher, fetcher, new JsonPackager());
        try {
            assertEquals(
                    testLoan,
                    loanFetcher.getLoanData(
                            TestEntityCreator.getTestBuyer(), TestEntityCreator.getTestCar(), 0));
        } catch (Exceptions.CodedException e) {
            fail(e.getMessage());
        }
    }
}
