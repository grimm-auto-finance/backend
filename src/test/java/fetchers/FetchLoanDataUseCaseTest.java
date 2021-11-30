package fetchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import constants.Exceptions;

import entities.LoanData;
import entities.TestEntityCreator;

import entitypackagers.Attributizer;
import entitypackagers.AttributizerFactory;
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
            System.out.println("hi");
            assertEquals(
                    testLoan,
                    loanFetcher.getLoanData(
                            TestEntityCreator.getTestBuyer(), TestEntityCreator.getTestCar()));
        } catch (Exceptions.CodedException e) {
            fail(e.getMessage());
        }
    }
}
