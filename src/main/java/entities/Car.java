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

    // The name and year of the Car
    private String name;
    private int year;

    // TODO: decide if we want to use Factory method instead
    /**
     * Constructs a new Car with the given price, name, year
     * and empty AddOns map.
     * @param price
     * @param name
     * @param year
     */
    public Car(int price, String name, int year) {
        this(price, name, year, new HashMap<>());
    }

    /**
     * Constructs a new Car with the given price, name, year
     * and AddOns map.
     * @param price
     * @param name
     * @param year
     * @param addOns
     */
    public Car(int price, String name, int year, Map<String, AddOn> addOns) {
        this.price = price;
        this.name = name;
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
     * Returns this Car's name (Brand + Model)
     * @return
     */
    public String getName() {
        return name;
    }

    // TODO: are setters necessary? maybe should just make new Car object
    /**
     * Updates this Car's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns this Car's year
     * @return
     */
    public int getYear() {
        return year;
    }

    /**
     * Updates this Car's year
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }
}
