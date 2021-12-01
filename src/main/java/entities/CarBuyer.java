// Layer: entities
package entities;

import constants.EntityStringNames;

/**
 * A class to represent an individual person buying a car Stores all necessary information about the
 * person that is used by the Senso API and our functions to determine the returned loan.
 */
public class CarBuyer extends Entity {

    private final double budget;
    private final int creditScore;
    private final double downpayment;

    /**
     * Constructs a new CarBuyer with the specified budget and credit score.
     *
     * @param budget The car buyer's budget for the car with addons
     * @param creditScore The car buyer's credit score
     * @param downpayment The car buyer's down payment amount
     */
    protected CarBuyer(double budget, int creditScore, double downpayment) {
        this.budget = budget;
        this.creditScore = creditScore;
        this.downpayment = downpayment;
    }

    /**
     * Returns this CarBuyer's budget.
     *
     * @return the car buyer's budget.
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Returns this CarBuyer's credit score.
     *
     * @return the car buyer's credit score.
     */
    public int getCreditScore() {
        return creditScore;
    }

    /**
     * Returns this CarBuyer's down payment amount.
     *
     * @return the car buyer's down payment amount.
     */
    public double getDownPayment() {
        return downpayment;
    }

    @Override
    public String getStringName() {
        return EntityStringNames.BUYER_STRING;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CarBuyer)) {
            return false;
        }
        CarBuyer otherBuyer = (CarBuyer) other;

        return (this.budget == otherBuyer.budget)
                && (this.creditScore == otherBuyer.creditScore)
                && (this.downpayment == otherBuyer.downpayment);
    }
}
