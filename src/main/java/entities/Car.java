// layer: entities
package entities;

import constants.EntityStringNames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to represent an individual car that is being viewed and configured in the user interface.
 */
public class Car extends Entity {
    // A Map between add-on names and AddOn objects representing those addons
    private final Map<String, AddOn> addOns;

    // The Car's price, in dollars.
    private double price;

    // The make, model and year of the Car
    private final String make;
    private final String model;
    private final int year;
    private final double kilometres;
    private final int id;

    /**
     * Constructs a new Car with the given price, name, year and empty AddOns map. Price of the car
     * initially set to -1 as price of the car is not know during initialization
     *
     * @param kilometres The mileage of the car in kilometres
     * @param price The price of the car
     * @param make The make of the car
     * @param model The model of the car
     * @param year The model year of the car
     * @param id The id of car as its stored in the database
     */
    protected Car(double kilometres, double price, String make, String model, int year, int id) {
        this(kilometres, price, make, model, year, new HashMap<>(), id);
    }

    /**
     * Constructs a new Car with the given price, name, year and AddOns map.
     *
     * @param kilometres The mileage of the car in kilometres
     * @param price The price of the car
     * @param make The make of the car
     * @param model The model of the car
     * @param year The model year of the car
     * @param addOns A mapping of addon names to AddOn objects
     * @param id The id of car as its stored in the database
     */
    public Car(
            double kilometres,
            double price,
            String make,
            String model,
            int year,
            Map<String, AddOn> addOns,
            int id) {
        this.kilometres = kilometres;
        this.price = price;
        this.make = make;
        this.model = model;
        this.year = year;
        this.addOns = addOns;
        this.id = id;
    }

    /**
     * Calculates the total price of the car by adding the price of the addons
     *
     * @return The total price of the car including addons
     */
    public double totalPrice() {
        double totalPrice = this.price;
        for (String key : this.addOns.keySet()) {
            totalPrice += this.addOns.get(key).getPrice();
        }
        return totalPrice;
    }

    /**
     * Adds the given AddOn to this Car's set of AddOns
     *
     * @param addOn The addon to be added to the addon map
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
     *
     * @return The addon map of the car
     */
    public Map<String, AddOn> getAddOns() {
        return new HashMap<>(this.addOns);
    }

    public List<AddOn> getAddOnsList() {
        List<AddOn> addOnsList = new ArrayList<>();
        for (String s : this.addOns.keySet()) {
            addOnsList.add(addOns.get(s));
        }
        return addOnsList;
    }

    /**
     * Returns this Car's price, in dollars.
     *
     * @return The price of the car in dollars
     */
    public double getPrice() {
        return price;
    }

    /**
     * Updates this Car's price to the specified value.
     *
     * @param price The car's price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * returns the make of this car
     *
     * @return The make of the car
     */
    public String getMake() {
        return make;
    }

    /**
     * returns the model of this car
     *
     * @return The model of the car
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns this Car's model year
     *
     * @return The car's model year
     */
    public int getYear() {
        return year;
    }

    public double getKilometres() {
        return kilometres;
    }

    public int getId() {
        return id;
    }

    /** @return Returns the string name of the car */
    @Override
    public String getStringName() {
        return EntityStringNames.CAR_STRING;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Car)) {
            return false;
        }
        Car otherCar = (Car) other;
        for (String s : addOns.keySet()) {
            if (!otherCar.addOns.containsKey(s)) {
                return false;
            }
            if (!addOns.get(s).equals(otherCar.addOns.get(s))) {
                return false;
            }
        }
        return (this.make.equals(otherCar.make))
                && (this.model.equals(otherCar.model))
                && (this.year == otherCar.year)
                && (this.price == otherCar.price);
    }
}
