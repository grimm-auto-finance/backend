package entities;

import constants.EntityStringNames;

/**
 * A class to represent an individual person buying a car Stores all necessary information about the
 * person that is used by the Senso API and our functions to determine the returned loan.
 */
public class CarBuyer extends Entity {

    private final double budget;
    private final int creditScore;

    /**
     * Constructs a new CarBuyer with the specified budget and credit score.
     *
     * @param budget The car buyers budget for the car with addons
     * @param creditScore The car buyers credit score
     */
    public CarBuyer(double budget, int creditScore) {
        this.budget = budget;
        this.creditScore = creditScore;
    }

    /**
     * Returns this CarBuyer's budget.
     *
     * @return the car buyers budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Returns this CarBuyer's credit score.
     *
     * @return the car buyers credit score
     */
    public int getCreditScore() {
        return creditScore;
    }

    @Override
    public String getStringName() {
        return EntityStringNames.BUYER_STRING;
    }
}
