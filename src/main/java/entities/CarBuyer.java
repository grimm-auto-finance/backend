package entities;

/**
 * A class to represent an individual person buying a car
 * Stores all necessary information about the person that is
 * used by the Senso API and our functions to determine the
 * returned loan.
 */
public class CarBuyer {

    private Double budget;
    private int creditScore;

    /**
     * Constructs a new CarBuyer with the specified
     * budget and credit score.
     * @param budget
     * @param creditScore
     */
    public CarBuyer(Double budget, int creditScore) {
        this.budget = budget;
        this.creditScore = creditScore;
    }

    /**
     * Returns this CarBuyer's budget.
     * @return
     */
    public Double getBudget() {
        return budget;
    }

    //TODO: Is this needed?
    /**
     * Updates the CarBuyer's budget to the specified value.
     * @param budget
     */
    public void setBudget(Double budget) {
        this.budget = budget;
    }

    /**
     * Returns this CarBuyer's credit score.
     * @return
     */
    public int getCreditScore() {
        return creditScore;
    }

    //TODO: is this needed?
    /**
     * Updates this CarBuyer's credit score to the specified value.
     * @param creditScore
     */
    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }
}
