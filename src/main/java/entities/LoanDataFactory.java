package entities;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanDataFactory {

    /**
     * Constructs a new LoanData using the values in the given AttributeMap
     * @param map an AttributeMap containing keys and values corresponding to the attributes of a LoanData
     * @return a LoanData constructed from the values in map
     * @throws Exceptions.FactoryException if the required key/value pairs for LoanData construction aren't present in map
     */
    public static LoanData getEntity(AttributeMap map) throws Exceptions.FactoryException {
        double interestRate;
        double installment;
        String sensoScore;
        double loanAmount;
        int termLength;
        double interestSum;
        List<Map<String, Double>> amortizationTable = new ArrayList<>();

        try {
            interestRate = (double) map.getItem(EntityStringNames.LOAN_INTEREST_RATE).getAttribute();
            installment = (double) map.getItem(EntityStringNames.LOAN_INSTALLMENT).getAttribute();
            sensoScore = (String) map.getItem(EntityStringNames.LOAN_SCORE).getAttribute();
            loanAmount = (double) map.getItem(EntityStringNames.LOAN_AMOUNT).getAttribute();
            termLength = (int) Math.round((double) map.getItem(EntityStringNames.LOAN_TERM_LENGTH).getAttribute());
            interestSum = (double) map.getItem(EntityStringNames.LOAN_INTEREST_SUM).getAttribute();
            ArrayAttribute amortizationArray = (ArrayAttribute) map.getItem(EntityStringNames.LOAN_AMORTIZATION);
            Attribute[] amortization = amortizationArray.getAttribute();
            for (Attribute a : amortization) {
                AttributeMap installmentAttMap = (AttributeMap) a;
                Map<String, Attribute> installmentMap = installmentAttMap.getAttribute();
                Map<String, Double> installmentDoubleMap = new HashMap<>();
                for (String s : installmentMap.keySet()) {
                    installmentDoubleMap.put(s, (double) installmentMap.get(s).getAttribute());
                }
                amortizationTable.add(installmentDoubleMap);
            }
        } catch (ClassCastException | NullPointerException e) {
            Exceptions.FactoryException ex = new Exceptions.FactoryException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }

        return new LoanData(interestRate, installment, sensoScore, loanAmount, termLength, interestSum, amortizationTable);
    }
}
