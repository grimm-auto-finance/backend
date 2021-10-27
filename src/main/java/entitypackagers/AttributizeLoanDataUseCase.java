package entitypackagers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.LoanData;

public class AttributizeLoanDataUseCase implements Attributizer {

    private final LoanData loan;

    public AttributizeLoanDataUseCase(LoanData loan) {
        this.loan = loan;
    }

    public AttributeMap attributizeEntity() {
        AttributeMap loanMap = new AttributeMap();
        loanMap.addItem(EntityStringNames.LOAN_AMOUNT, loan.getLoanAmount());
        loanMap.addItem(EntityStringNames.LOAN_INSTALLMENT, loan.getInstallment());
        loanMap.addItem(EntityStringNames.LOAN_SCORE, loan.getSensoScore());
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_RATE, loan.getInterestRate());
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_SUM, loan.getInterestSum());
        loanMap.addItem(EntityStringNames.LOAN_TERM_LENGTH, loan.getTermLength());
        return loanMap;
    }
}
