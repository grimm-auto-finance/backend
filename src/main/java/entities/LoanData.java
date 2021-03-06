// layer: entities
package entities;

import constants.EntityStringNames;

import java.util.List;
import java.util.Map;

/**
 * A class to represent a loan, as determined by the Senso API /rate and /score functions based on a
 * given CarBuyer and Car.
 */
public class LoanData extends Entity {
    private double interestRate;
    private double installment;
    private String sensoScore;
    private double loanAmount;
    private int termLength;
    private double interestSum;
    private double addOnBudget;
    private final List<Map<String, Double>> amortizationTable;

    /**
     * Constructs a new LoanData object with the given values.
     *
     * @param interestRate the interest rate of the loan
     * @param installment the monthly installment value of the loan
     * @param sensoScore the senso score of the loan
     * @param loanAmount the principal value of the loan
     * @param termLength the length of the loan's term
     * @param interestSum the total amount of interest paid on the loan
     * @param addOnBudget the remaining budget for add-ons on this loan
     * @param amortizationTable the amortization table for this loan
     */
    protected LoanData(
            double interestRate,
            double installment,
            String sensoScore,
            double loanAmount,
            int termLength,
            double interestSum,
            double addOnBudget,
            List<Map<String, Double>> amortizationTable) {
        this.interestRate = interestRate;
        this.installment = installment;
        this.sensoScore = sensoScore;
        this.loanAmount = loanAmount;
        this.termLength = termLength;
        this.interestSum = interestSum;
        this.addOnBudget = addOnBudget;
        this.amortizationTable = amortizationTable;
    }

    /**
     * Constructs a new LoanData object with the given values. addOnBudget is initialized to 0
     *
     * @param interestRate the interest rate of the loan
     * @param installment the monthly installment value of the loan
     * @param sensoScore the senso score of the loan
     * @param loanAmount the principal value of the loan
     * @param termLength the length of the loan's term
     * @param interestSum the total amount of interest paid on the loan
     * @param amortizationTable the amortization table for this loan
     */
    protected LoanData(
            double interestRate,
            double installment,
            String sensoScore,
            double loanAmount,
            int termLength,
            double interestSum,
            List<Map<String, Double>> amortizationTable) {
        this(
                interestRate,
                installment,
                sensoScore,
                loanAmount,
                termLength,
                interestSum,
                0.0,
                amortizationTable);
    }

    /**
     * Returns this LoanData's interest rate.
     *
     * @return The interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Updates this LoanData's interest rate with the given value.
     *
     * @param interestRate The interest rate
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Returns this loan's monthly installment amount
     *
     * @return The installment amount
     */
    public double getInstallment() {
        return installment;
    }

    /**
     * Updates this loan's monthly installment amount with the given value
     *
     * @param installment The installment amount
     */
    public void setInstallment(double installment) {
        this.installment = installment;
    }

    /**
     * Returns this loan's amortization table
     *
     * @return The loan's amortization table
     */
    public List<Map<String, Double>> getAmortizationTable() {
        return amortizationTable;
    }

    /**
     * Returns this loan's senso score
     *
     * @return The loan's senso score
     */
    public String getSensoScore() {
        return sensoScore;
    }

    /**
     * Updates this loan's senso score with the given value
     *
     * @param sensoScore The loan's senso score
     */
    public void setSensoScore(String sensoScore) {
        this.sensoScore = sensoScore;
    }

    /**
     * Returns this loan's principal dollar value
     *
     * @return The loan's value
     */
    public double getLoanAmount() {
        return loanAmount;
    }

    /**
     * Updates this loan's principal dollar value
     *
     * @param loanAmount The loan's value
     */
    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * Returns this loan's term length
     *
     * @return The loan's term length
     */
    public int getTermLength() {
        return termLength;
    }

    /**
     * Updates this loan's term length
     *
     * @param termLength The loan's term length
     */
    public void setTermLength(int termLength) {
        this.termLength = termLength;
    }

    /**
     * Returns the total amount of interest to be paid on this loan
     *
     * @return The amount of interest to be paid
     */
    public double getInterestSum() {
        return interestSum;
    }

    /**
     * Updates the total amount of interest to be paid on this loan
     *
     * @param interestSum The amount of interest to be paid
     */
    public void setInterestSum(double interestSum) {
        this.interestSum = interestSum;
    }

    /**
     * Returns the budget left for add-ons on this loan.
     *
     * @return the budget for add-ons, in dollars
     */
    public double getAddOnBudget() {
        return addOnBudget;
    }

    /**
     * Updates this loan's budget for addons
     *
     * @param budget the new budget
     */
    public void setAddOnBudget(double budget) {
        this.addOnBudget = budget;
    }

    @Override
    public String getStringName() {
        return EntityStringNames.LOAN_STRING;
    }

    /** Returns whether this LoanData is equal to other */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LoanData)) {
            return false;
        }
        LoanData otherLoan = (LoanData) other;
        for (Map<String, Double> firstEntry : amortizationTable) {
            boolean amortizationMatch = false;
            for (Map<String, Double> secondEntry : otherLoan.amortizationTable) {
                if (compareAmortizationEntries(firstEntry, secondEntry)) {
                    amortizationMatch = true;
                }
            }
            if (!amortizationMatch) {
                return false;
            }
        }

        return (Math.abs(this.interestRate - otherLoan.interestRate) < .001)
                && (Math.abs(this.installment - otherLoan.installment) < .001)
                && (Math.abs(this.loanAmount - otherLoan.loanAmount) < .001)
                && (Math.abs(this.interestSum - otherLoan.interestSum) < .001)
                && (Math.abs(this.addOnBudget - otherLoan.addOnBudget) < .001)
                && (this.termLength == otherLoan.termLength)
                && (this.sensoScore.equals(otherLoan.sensoScore));
    }

    private boolean compareAmortizationEntries(
            Map<String, Double> first, Map<String, Double> second) {
        for (String s : first.keySet()) {
            if (!second.containsKey(s)) {
                return false;
            }
            if (Math.abs(first.get(s) - second.get(s)) >= .001) {
                return false;
            }
        }
        return true;
    }
}
