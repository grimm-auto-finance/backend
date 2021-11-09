package entitybuilder;

import entities.Car;

/** A class to generate a Car object upon request from the controller */
public class GenerateCarUseCase {
    /**
     * @param kilometres The milage of the car being bought in kilometres
     * @param price The price of the car being bought
     * @param make The make of the car being bought
     * @param model The model of the car being bought
     * @param year The year of the car model
     * @return A new Car object with the provided name and model year
     */
    public Car GenerateCarUseCase(
            double kilometres, double price, String make, String model, int year) {
        return new Car(kilometres, price, make, model, year);
    }
}
