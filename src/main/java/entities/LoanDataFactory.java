package entities;

import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;

public class LoanDataFactory implements EntityFactory {

    public LoanData getEntity(AttributeMap map) throws Exceptions.FactoryException {
        double interestRate;
        double installment;
        String sensoScore;
        double loanAmount;
        int termLength;
        double interestSum;

        try {
            interestRate = (double) map.getItem(EntityStringNames.LOAN_INTEREST_RATE).getAttribute();
            installment = (double) map.getItem(EntityStringNames.LOAN_INSTALLMENT).getAttribute();
            sensoScore = (String) map.getItem(EntityStringNames.LOAN_SCORE).getAttribute();
            loanAmount = (double) map.getItem(EntityStringNames.LOAN_AMOUNT).getAttribute();
            termLength = (int) Math.round((double) map.getItem(EntityStringNames.LOAN_TERM_LENGTH).getAttribute());
            interestSum = (double) map.getItem(EntityStringNames.LOAN_INTEREST_SUM).getAttribute();
        } catch (ClassCastException | NullPointerException e) {
            Exceptions.FactoryException ex = new Exceptions.FactoryException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }

        return new LoanData(interestRate, installment, sensoScore, loanAmount, termLength, interestSum);
    }
}
