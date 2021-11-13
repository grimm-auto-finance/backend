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
    private List<Map<String, Double>> amortizationTable;

    /**
     * Constructs a new LoanData object with the given values.
     *
     * @param interestRate the interest rate of the loan
     * @param installment the monthly installment value of the loan
     * @param sensoScore the senso score of the loan
     * @param loanAmount the principal value of the loan
     * @param termLength the length of the loan's term
     * @param interestSum the total amount of interest paid on the loan
     * @param amortizationTable the ammortization table for this loan
     */
    protected LoanData(
            double interestRate,
            double installment,
            String sensoScore,
            double loanAmount,
            int termLength,
            double interestSum,
            List<Map<String, Double>> amortizationTable) {
        this.interestRate = interestRate;
        this.installment = installment;
        this.sensoScore = sensoScore;
        this.loanAmount = loanAmount;
        this.termLength = termLength;
        this.interestSum = interestSum;
        this.amortizationTable = amortizationTable;
    }

    /**
     * Returns this LoanData's interest rate.
     *
     * @return
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Updates this LoanData's interest rate with the given value.
     *
     * @param interestRate
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Returns this loan's monthly installment amount
     *
     * @return
     */
    public double getInstallment() {
        return installment;
    }

    /**
     * Updates this loan's monthly installment amount with the given value
     *
     * @param installment
     */
    public void setInstallment(double installment) {
        this.installment = installment;
    }

    /**
     * Returns this loan's amortization table
     *
     * @return
     */
    public List<Map<String, Double>> getAmortizationTable() {
        return amortizationTable;
    }

    /**
     * Returns this loan's senso score
     *
     * @return
     */
    public String getSensoScore() {
        return sensoScore;
    }

    /**
     * Updates this loan's senso score with the given value
     *
     * @param sensoScore
     */
    public void setSensoScore(String sensoScore) {
        this.sensoScore = sensoScore;
    }

    /**
     * Returns this loan's principal dollar value
     *
     * @return
     */
    public double getLoanAmount() {
        return loanAmount;
    }

    /**
     * Updates this loan's principal dollar value
     *
     * @param loanAmount
     */
    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * Returns this loan's term length
     *
     * @return
     */
    public int getTermLength() {
        return termLength;
    }

    /**
     * Updates this loan's term length
     *
     * @param termLength
     */
    public void setTermLength(int termLength) {
        this.termLength = termLength;
    }

    /**
     * Returns the total amount of interest to be paid on this loan
     *
     * @return
     */
    public double getInterestSum() {
        return interestSum;
    }

    /**
     * Updates the total amount of interest to be paid on this loan
     *
     * @param interestSum
     */
    public void setInterestSum(double interestSum) {
        this.interestSum = interestSum;
    }

    @Override
    public String getStringName() {
        return EntityStringNames.LOAN_STRING;
    }

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
                && (this.termLength == otherLoan.termLength)
                && (this.sensoScore.equals(otherLoan.sensoScore));
    }

    private boolean compareAmortizationEntries(Map<String, Double> first, Map<String, Double> second) {
        return compareAmortizationEntries(first, second, .001);
    }

    private boolean compareAmortizationEntries(Map<String, Double> first, Map<String, Double> second, double epsilon) {
        for (String s : first.keySet()) {
            if (!second.containsKey(s)) {
                return false;
            }
            if (Math.abs(first.get(s) - second.get(s)) >= epsilon) {
                return false;
            }
        }
        return true;
    }
}
