package entities;

import constants.EntityStringNames;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to represent an individual car that is being viewed and configured in the user interface.
 */
public class Car extends Entity {
    // A Map between add-on names and AddOn objects representing those addons
    private Map<String, AddOn> addOns;

    // The Car's price, in dollars.
    private double price;

    // The make, model and year of the Car
    private final String make;
    private final String model;
    private final int year;

    // TODO: decide if we want to use Factory method instead
    /**
     * Constructs a new Car with the given price, name, year and empty AddOns map. Price of the car
     * initially set to -1 as price of the car is not know during initialization
     *
     * @param make The make of the car
     * @param model The model of the car
     * @param year The model year of the car
     */
    public Car(double price, String make, String model, int year) {
        this(price, make, model, year, new HashMap<>());
    }

    /**
     * Constructs a new Car with the given price, name, year and AddOns map.
     *
     * @param price The price of the car
     * @param make The make of the car
     * @param model The model of the car
     * @param year The model year of the car
     * @param addOns A mapping of addon names to AddOn objects
     */
    public Car(double price, String make, String model, int year, Map<String, AddOn> addOns) {
        this.price = price;
        this.make = make;
        this.model = model;
        this.year = year;
        this.addOns = addOns;
    }

    /**
     * Adds the given AddOn to this Car's set of AddOns
     *
     * @param addOn
     */
    public void addAddOn(AddOn addOn) {
        addOns.put(addOn.getName(), addOn);
    }

    /**
     * Removes the given AddOn from this Car's set of AddOns
     *
     * @param addOnName the name of the AddOn to be removed
     */
    public void removeAddOn(String addOnName) {
        addOns.remove(addOnName);
    }

    /**
     * Returns the AddOns stored in this Car
     * @return
     */
    public Map<String, AddOn> getAddOns() {
        return new HashMap<>(this.addOns);
    }

    /**
     * Returns this Car's price, in dollars.
     *
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * Updates this Car's price to the specified value.
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * returns the make of this car
     *
     * @return
     */
    public String getMake() {
        return make;
    }

    /**
     * returns the model of this car
     *
     * @return
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns this Car's model year
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    @Override
    public String getStringName() {
        return EntityStringNames.CAR_STRING;
    }
}
