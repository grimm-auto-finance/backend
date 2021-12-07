package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanDataFactoryTest {

    @Test
    public void testGetEntityWorking() {
        AttributeMap loanMap = new AttributeMap();
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_RATE, 1.5);
        // TODO: update this to be an int once we have int/double parsing figured out
        loanMap.addItem(EntityStringNames.LOAN_TERM_LENGTH, 36);
        loanMap.addItem(EntityStringNames.LOAN_INSTALLMENT, 300.25);
        loanMap.addItem(EntityStringNames.LOAN_AMOUNT, 10000.0);
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_SUM, 200.5);
        loanMap.addItem(EntityStringNames.LOAN_SCORE, "very high");
        AttributeMap installmentMap = new AttributeMap();
        installmentMap.addItem("installment", 300.25);
        installmentMap.addItem("interestSum", 200.5);
        Attribute[] installments = {installmentMap};
        ArrayAttribute amortizationArray =
                (ArrayAttribute) AttributeFactory.createAttribute(installments);
        loanMap.addItem(EntityStringNames.LOAN_AMORTIZATION, amortizationArray);

        Map<String, Double> installmentHashMap = new HashMap<>();
        installmentHashMap.put("installment", 300.25);
        installmentHashMap.put("interestSum", 200.5);
        List<Map<String, Double>> amortizationList = new ArrayList<>();
        amortizationList.add(installmentHashMap);
        LoanData testLoan =
                new LoanData(1.5, 300.25, "very high", 10000.0, 36, 200.5, amortizationList);

        try {
            assertEquals(testLoan, LoanDataFactory.getEntity(loanMap));
        } catch (Exceptions.FactoryException e) {
            fail();
        }
    }

    @Test
    public void testGetEntityMissingKeys() {
        AttributeMap loanMap = new AttributeMap();
        loanMap.addItem("wrong key!", "this won't work");
        try {
            LoanDataFactory.getEntity(loanMap);
        } catch (Exceptions.FactoryException e) {
            return;
        }
        fail();
    }

    @Test
    public void testGetEntityWrongValueTypes() {
        AttributeMap loanMap = new AttributeMap();
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_RATE, "uh oh");
        loanMap.addItem(EntityStringNames.LOAN_TERM_LENGTH, "not good");
        loanMap.addItem(EntityStringNames.LOAN_INSTALLMENT, "yikes");
        loanMap.addItem(EntityStringNames.LOAN_AMOUNT, "oh no");
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_SUM, "i should not be a string");
        loanMap.addItem(EntityStringNames.LOAN_SCORE, 20);
        loanMap.addItem(
                EntityStringNames.LOAN_AMORTIZATION, AttributeFactory.createAttribute("crap"));

        try {
            LoanDataFactory.getEntity(loanMap);
        } catch (Exceptions.FactoryException e) {
            return;
        }
        fail();
    }
}
