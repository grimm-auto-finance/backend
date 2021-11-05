package entitybuilder;

import entities.LoanData;

import java.util.List;
import java.util.Map;

/** Class to generate an empty LoanData object */
public class GenerateLoanUseCase {
    /**
     * Constructor that generates a LoanData object with the given parameters
     *
     * @return Returns a Loan Data object
     */
    public static LoanData generateLoanData(
            double interestRate,
            double installment,
            String sensoScore,
            double loanAmount,
            int termLength,
            double interestSum,
            List<Map<String, Double>> amortizationTable) {
        return new LoanData(
                interestRate, installment, sensoScore, loanAmount, termLength, interestSum, amortizationTable);
    }
}
