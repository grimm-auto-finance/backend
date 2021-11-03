package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LoanDataTest {
    @Test
    public void gettersAndSetters() {
        LoanData loanData = new LoanData(5.5, 600, "Very high", 15000, 24, 3000);
        loanData.setLoanAmount(10000);
        loanData.setInstallment(500.45);
        loanData.setInterestRate(1.25);
        loanData.setInterestSum(2000);
        loanData.setSensoScore("Medium");
        loanData.setTermLength(36);
        assertEquals(10000, loanData.getLoanAmount());
        assertEquals(500.45, loanData.getInstallment());
        assertEquals(1.25, loanData.getInterestRate());
        assertEquals(2000, loanData.getInterestSum());
        assertEquals("Medium", loanData.getSensoScore());
        assertEquals(36, loanData.getTermLength());
    }

    @Test
    public void constructor2() {
        LoanData loanData = new LoanData(1.25, 500.45, "Medium", 10000, 36, 2000);
        assertEquals(10000, loanData.getLoanAmount());
        assertEquals(500.45, loanData.getInstallment());
        assertEquals(1.25, loanData.getInterestRate());
        assertEquals(2000, loanData.getInterestSum());
        assertEquals("Medium", loanData.getSensoScore());
        assertEquals(36, loanData.getTermLength());
    }
}
