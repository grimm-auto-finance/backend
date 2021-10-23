package entitybuilder;

import entities.LoanData;

/** Class to generate an empty LoanData object */
public class GenerateLoanUseCase {
    /**
     * Constructor that generates the empty LoanData and returns it
     *
     * @return Returns an empty Loan Data object
     */
    public LoanData GenerateLoanDataUseCase() {
        return new LoanData();
    }
}
