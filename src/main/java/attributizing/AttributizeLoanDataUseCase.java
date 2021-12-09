// layer: usecases
package attributizing;

import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.LoanData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** A use case to convert a LoanData into an AttributeMap */
public class AttributizeLoanDataUseCase implements Attributizer {

    private final LoanData loan;

    /**
     * Constructs a new AttributizeLoanDataUseCase to write the given LoanData to an AttributeMap
     *
     * @param loan the LoanData to be serialized
     */
    public AttributizeLoanDataUseCase(LoanData loan) {
        this.loan = loan;
    }

    /**
     * Writes the stored LoanData to an AttributeMap
     *
     * @return an AttributeMap representing the LoanData
     */
    public AttributeMap attributizeEntity() {
        AttributeMap loanMap = new AttributeMap();
        loanMap.addItem(EntityStringNames.LOAN_AMOUNT, loan.getLoanAmount());
        loanMap.addItem(EntityStringNames.LOAN_INSTALLMENT, loan.getInstallment());
        loanMap.addItem(EntityStringNames.LOAN_SCORE, loan.getSensoScore());
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_RATE, loan.getInterestRate());
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_SUM, loan.getInterestSum());
        loanMap.addItem(EntityStringNames.LOAN_TERM_LENGTH, loan.getTermLength());
        loanMap.addItem(EntityStringNames.LOAN_ADD_ON_BUDGET, loan.getAddOnBudget());
        List<Map<String, Double>> amortizationTable = loan.getAmortizationTable();
        List<AttributeMap> amortizationAttMap = getAmortizationAttMap(amortizationTable);
        loanMap.addItem(
                EntityStringNames.LOAN_AMORTIZATION, amortizationAttMap.toArray(new Attribute[0]));
        return loanMap;
    }

    /**
     * Converts an amortization table, in the form of a List of Maps between Strings and Doubles, to
     * a List of AttributeMaps.
     *
     * @param amortizationTable the List of Maps between strings and Doubles to be attributized
     * @return a List of AttributeMaps, where each AttributeMap represents one of the Maps in
     *     amortizationTable
     */
    public static List<AttributeMap> getAmortizationAttMap(
            List<Map<String, Double>> amortizationTable) {
        List<AttributeMap> amortizationAttMap = new ArrayList<>();
        for (Map<String, Double> m : amortizationTable) {
            AttributeMap installmentMap = new AttributeMap();
            for (String s : m.keySet()) {
                installmentMap.addItem(s, m.get(s));
            }
            amortizationAttMap.add(installmentMap);
        }
        return amortizationAttMap;
    }
}
