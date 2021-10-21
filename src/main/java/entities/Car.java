package entities;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to represent an individual car that is being
 * viewed and configured in the user interface.
 */
public class Car {
    // A Map between add-on names and AddOn objects representing those addons
    private Map<String, AddOn> addOns;

    // The Car's price, in dollars.
    private int price;

    // The make, model and year of the Car
    final private String make;
    final private String model;
    final private int year;

    // TODO: decide if we want to use Factory method instead
    /**
     * Constructs a new Car with the given price, name, year
     * and empty AddOns map.
     * @param make
     * @param model
     * @param year
     */
    public Car(String make, String model, int year) {
        this(-1, make, model, year, new HashMap<>());
    }

    /**
     * Constructs a new Car with the given price, name, year
     * and AddOns map.
     * @param price
     * @param make
     * @param model
     * @param year
     * @param addOns
     */
    public Car(int price, String make, String model, int year, Map<String, AddOn> addOns) {
        this.price = price;
        this.make = make;
        this.model = model;
        this.year = year;
        this.addOns = addOns;
    }

    /**
     * Adds the given AddOn to this Car's set of AddOns
     * @param addOn
     */
    public void addAddOn(AddOn addOn) {
        addOns.put(addOn.getName(), addOn);
    }

    /**
     * Removes the given AddOn from this Car's set of AddOns
     * @param addOn
     */
    public void removeAddOn(AddOn addOn) {
        addOns.remove(addOn.getName());
    }

    /**
     * Returns this Car's price, in dollars.
     * @return
     */
    public int getPrice() {
        return price;
    }

    /**
     * Updates this Car's price to the specified value.
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * returns the make of this car
     * @return
     */
    public String getMake() {
        return make;
    }

    /**
     * returns the model of this car
     * @return
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns this Car's model year
     * @return
     */

    public int getYear() {
        return year;
    }


}
