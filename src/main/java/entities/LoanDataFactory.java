// layer: usecases
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
     *
     * @param map an AttributeMap containing keys and values corresponding to the attributes of a
     *     LoanData
     * @return a LoanData constructed from the values in map
     * @throws Exceptions.FactoryException if the required key/value pairs for LoanData construction
     *     aren't present in map
     */
    public static LoanData getEntity(AttributeMap map) throws Exceptions.FactoryException {
        double interestRate;
        double installment;
        String sensoScore;
        double loanAmount;
        int termLength;
        double interestSum;
        double addOnBudget = 0;
        List<Map<String, Double>> amortizationTable = new ArrayList<>();

        try {
            interestRate =
                    AttributeMap.getDoubleMaybeInteger(EntityStringNames.LOAN_INTEREST_RATE, map);
            installment =
                    AttributeMap.getDoubleMaybeInteger(EntityStringNames.LOAN_INSTALLMENT, map);
            sensoScore = (String) map.getItem(EntityStringNames.LOAN_SCORE).getAttribute();
            loanAmount = AttributeMap.getDoubleMaybeInteger(EntityStringNames.LOAN_AMOUNT, map);
            termLength = (int) map.getItem(EntityStringNames.LOAN_TERM_LENGTH).getAttribute();
            try {
                addOnBudget = (double) map.getItem(EntityStringNames.LOAN_ADD_ON_BUDGET).getAttribute();
            } catch (NullPointerException ignored) {
                // if add-on budget isn't in the map we just default its value to 0
            }
            interestSum =
                    AttributeMap.getDoubleMaybeInteger(EntityStringNames.LOAN_INTEREST_SUM, map);
            ArrayAttribute amortizationArray =
                    (ArrayAttribute) map.getItem(EntityStringNames.LOAN_AMORTIZATION);
            Attribute[] amortization = amortizationArray.getAttribute();
            for (Attribute a : amortization) {
                AttributeMap installmentAttMap = (AttributeMap) a;
                Map<String, Attribute> installmentMap = installmentAttMap.getAttribute();
                Map<String, Double> installmentDoubleMap = new HashMap<>();
                for (String s : installmentMap.keySet()) {
                    installmentDoubleMap.put(
                            s, AttributeMap.getDoubleMaybeInteger(s, installmentAttMap));
                }
                amortizationTable.add(installmentDoubleMap);
            }
        } catch (ClassCastException | NullPointerException e) {
            String message = "Failed to generate LoanData";
            throw new Exceptions.FactoryException(message, e);
        }

        return new LoanData(
                interestRate,
                installment,
                sensoScore,
                loanAmount,
                termLength,
                interestSum, addOnBudget,
                amortizationTable);
    }
}
