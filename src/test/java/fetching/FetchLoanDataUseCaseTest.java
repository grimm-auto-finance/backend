package fetching;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import constants.Exceptions;

import entities.LoanData;
import entities.TestEntityCreator;

import org.junit.jupiter.api.Test;

import packaging.JsonPackager;

public class FetchLoanDataUseCaseTest {

    @Test
    public void testFetchNoBudget() {
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

    @Test
    public void testFetchWithBudget() {
        LoanData testLoan = TestEntityCreator.getTestLoanData();
        testLoan.setAddOnBudget(5000);
        FakeHttpFetcher fetcher = new FakeHttpFetcher();
        FetchLoanDataUseCase loanFetcher =
                new FetchLoanDataUseCase(fetcher, fetcher, new JsonPackager());
        try {
            assertEquals(
                    testLoan,
                    loanFetcher.getLoanData(
                            TestEntityCreator.getTestBuyer(), TestEntityCreator.getTestCar(), 2));
        } catch (Exceptions.CodedException e) {
            fail(e.getMessage());
        }
    }
}
