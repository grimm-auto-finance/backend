package entitypackagers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.LoanData;

import entities.TestEntityCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AttributizeLoanDataUseCaseTest {

    static AttributeMap testMap;
    static LoanData loanData;
    static AttributizeLoanDataUseCase loanDataAttributizer;

    @BeforeEach
    public void setup() {
        testMap = new AttributeMap();
    }

    static void addLoanDataToTestMap() {
        testMap.addItem(EntityStringNames.LOAN_AMOUNT, loanData.getLoanAmount());
        testMap.addItem(EntityStringNames.LOAN_INSTALLMENT, loanData.getInstallment());
        testMap.addItem(EntityStringNames.LOAN_SCORE, loanData.getSensoScore());
        testMap.addItem(EntityStringNames.LOAN_INTEREST_RATE, loanData.getInterestRate());
        testMap.addItem(EntityStringNames.LOAN_INTEREST_SUM, loanData.getInterestSum());
        testMap.addItem(EntityStringNames.LOAN_TERM_LENGTH, loanData.getTermLength());
        List<AttributeMap> amortizationAttMap =
                AttributizeLoanDataUseCase.getAmortizationAttMap(loanData.getAmortizationTable());
        testMap.addItem(
                EntityStringNames.LOAN_AMORTIZATION, amortizationAttMap.toArray(new Attribute[0]));
    }

    @Test
    public void testAttributizeLoanData() {
        loanData = TestEntityCreator.getTestLoanData();
        addLoanDataToTestMap();
        loanDataAttributizer = new AttributizeLoanDataUseCase(loanData);
        assertEquals(
                testMap.getAttribute().toString(),
                loanDataAttributizer.attributizeEntity().getAttribute().toString());
    }
}
