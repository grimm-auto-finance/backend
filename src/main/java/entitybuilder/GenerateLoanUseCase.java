package entitybuilder;

import entities.LoanData;

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
      double interestSum) {
    return new LoanData(interestRate, installment, sensoScore, loanAmount, termLength, interestSum);
  }
}
