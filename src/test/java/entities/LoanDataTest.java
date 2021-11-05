package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import constants.EntityStringNames;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanDataTest {

    static LoanData loanData;

    @BeforeEach
    public void setup() {
        List<Map<String, Double>> ammortizationTable = new ArrayList<>();
        ammortizationTable.add(new HashMap<>());
        ammortizationTable.get(0).put("installment", 100.25);
        loanData = new LoanData(1.25,
                500.45,
                "Medium",
                10000,
                36,
                2000,
                ammortizationTable);
    }

    @Test
    public void testGetInterestRate() {
        assertEquals(1.25, loanData.getInterestRate());
    }

    @Test
    public void testSetInterestRate() {
        loanData.setInterestRate(5.6);
        assertEquals(5.6, loanData.getInterestRate());
    }

    @Test
    public void testGetInstallment() {
        assertEquals(500.45, loanData.getInstallment());
    }

    @Test
    public void testGetAmmortizationTable() {
        List<Map<String, Double>> ammortizationTable = new ArrayList<>();
        ammortizationTable.add(new HashMap<>());
        ammortizationTable.get(0).put("installment", 100.25);
        assertEquals(ammortizationTable.toString(), loanData.getAmmortizationTable().toString());
    }

    @Test
    public void testSetInstallment() {
        loanData.setInstallment(600.23);
        assertEquals(600.23, loanData.getInstallment());
    }

    @Test
    public void testGetLoanAmount() {
        assertEquals(10000, loanData.getLoanAmount());
    }

    @Test
    public void testSetLoanAmount() {
        loanData.setLoanAmount(15000.25);
        assertEquals(15000.25, loanData.getLoanAmount());
    }

    @Test
    public void testGetInterestSum() {
        assertEquals(2000, loanData.getInterestSum());
    }

    @Test
    public void testSetInterestSum() {
        loanData.setInterestSum(2500.25);
        assertEquals(2500.25, loanData.getInterestSum());
    }

    @Test
    public void testGetSensoScore() {
        assertEquals("Medium", loanData.getSensoScore());
    }

    @Test
    public void testSetSensoScore() {
        loanData.setSensoScore("Very high");
        assertEquals("Very high", loanData.getSensoScore());
    }

    @Test
    public void testGetTermLength() {
        assertEquals(36, loanData.getTermLength());
    }

    @Test
    public void testSetTermLength() {
        loanData.setTermLength(24);
        assertEquals(24, loanData.getTermLength());
    }

    @Test
    public void testGetStringName() {
        assertEquals(EntityStringNames.LOAN_STRING, loanData.getStringName());
    }
}
