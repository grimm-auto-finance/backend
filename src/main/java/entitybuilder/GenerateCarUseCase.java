package entitybuilder;

import entities.Car;

/**
 * A class to generate a Car object
 * upon request from the controller
 */
public class GenerateCarUseCase {
    /**
     *
     * @param name The name of the car being bought
     * @param year The year of the car model
     * @return A new Car object with the provided name and model year
     */
    public Car GenerateCarUseCase(String name, int year) {
        return new Car(0, name, year);
    }
}
