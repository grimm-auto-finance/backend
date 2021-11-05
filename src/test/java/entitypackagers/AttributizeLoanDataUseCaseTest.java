package entitypackagers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.LoanData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<AttributeMap> ammortizationAttMap = AttributizeLoanDataUseCase.getAmortizationAttMap(loanData.getAmortizationTable());
        testMap.addItem(EntityStringNames.LOAN_AMORTIZATION, ammortizationAttMap.toArray(new Attribute[0]));
    }

    @Test
    public void testAttributizeLoanData() {
        List<Map<String, Double>> ammortizationTable = new ArrayList<>();
        Map<String, Double> installment = new HashMap<>();
        installment.put("Test String", 5.5);
        ammortizationTable.add(installment);
        loanData = new LoanData(1.25, 500.25, "Medium", 50000, 36, 200.25, ammortizationTable);
        addLoanDataToTestMap();
        loanDataAttributizer = new AttributizeLoanDataUseCase(loanData);
        assertEquals(
                testMap.getAttribute().toString(),
                loanDataAttributizer.attributizeEntity().getAttribute().toString());
    }
}
