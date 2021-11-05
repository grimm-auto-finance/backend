package entitybuilder;

import entities.AddOn;

/** A class to generate AddOn objects */
public class GenerateAddOnsUseCase {

    /**
     * generates an object using the given name, price, and description
     *
     * @param name the name of this AddOn
     * @param price the price of this AddOn
     * @param description the description of this AddOn
     * @return an AddOn object constructed using the given parameters
     */
    public AddOn generateAddOn(String name, double price, String description) {
        return new AddOn(name, price, description);
    }
}
