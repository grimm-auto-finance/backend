package entitybuilder;

import entities.CarBuyer;

/** Generates a CarBuyer object upon request from the controller */
public class GenerateBuyerUseCase {
    /**
     * generates a carBuyer object using the budget and creditScore provided parameters
     *
     * @param budget The budget of the car buyer
     * @param creditScore The car buyers credit score
     * @return The CarBuyer object
     */
    public CarBuyer GenerateBuyerDataUseCase(double budget, int creditScore) {
        return new CarBuyer(budget, creditScore);
    }
}
