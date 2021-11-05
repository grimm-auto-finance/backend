package entitypackagers;

import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.LoanData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        List<Map<String, Double>> ammortizationTable = loan.getAmmortizationTable();
        List<AttributeMap> ammortizationAttMap = getAmmortizationAttMap(ammortizationTable);
        loanMap.addItem(EntityStringNames.LOAN_AMMORTIZATION, ammortizationAttMap.toArray(new Attribute[0]));
        return loanMap;
    }

    public static List<AttributeMap> getAmmortizationAttMap(List<Map<String, Double>> ammortizationTable) {
        List<AttributeMap> ammortizationAttMap = new ArrayList<>();
        for (Map<String, Double> m : ammortizationTable) {
            AttributeMap installmentMap = new AttributeMap();
            for (String s: m.keySet()) {
                installmentMap.addItem(s, m.get(s));
            }
            ammortizationAttMap.add(installmentMap);
        }
        return ammortizationAttMap;
    }
}
