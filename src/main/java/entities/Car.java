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
    /** A Map between add-on names and AddOn objects representing those addons */
    private final Map<String, AddOn> addOns;

    /** The Car's price, in dollars. */
    private double price;

    /** The make, model and year of the Car */
    private final String make;

    private final String model;
    private final String image;
    private final int year;

    private final double kilometres;

    /** The car's ID as is used in the database */
    private final int id;

    /**
     * Constructs a new Car with the given information and an empty AddOns map.
     *
     * @param kilometres The mileage of the car in kilometres
     * @param price The price of the car
     * @param make The make of the car
     * @param model The model of the car
     * @param image The image of the car
     * @param year The model year of the car
     * @param id The id of car as it's stored in the database
     */
    protected Car(
            double kilometres,
            double price,
            String make,
            String model,
            String image,
            int year,
            int id) {
        this(kilometres, price, make, model, image, year, new HashMap<>(), id);
    }

    /**
     * Constructs a new Car with the given information and AddOns map.
     *
     * @param kilometres The mileage of the car in kilometres
     * @param price The price of the car
     * @param make The make of the car
     * @param model The model of the car
     * @param image The image of the car
     * @param year The model year of the car
     * @param addOns A mapping of addon names to AddOn objects
     * @param id The id of car
     */
    public Car(
            double kilometres,
            double price,
            String make,
            String model,
            String image,
            int year,
            Map<String, AddOn> addOns,
            int id) {
        this.kilometres = kilometres;
        this.price = price;
        this.make = make;
        this.model = model;
        this.image = image;
        this.year = year;
        this.addOns = addOns;
        this.id = id;
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
     * Returns this Car's price, including any add-ons it has.
     *
     * @return the total price of this car with its add-ons, in dollars
     */
    public double getTotalPrice() {
        double totalPrice = price;
        for (String name : addOns.keySet()) {
            totalPrice += addOns.get(name).getPrice();
        }
        return totalPrice;
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
     * returns the image of this car
     *
     * @return The image of the car
     */
    public String getImage() {
        return image;
    }

    /**
     * Returns this Car's model year
     *
     * @return The car's model year
     */
    public int getYear() {
        return year;
    }

    /**
     * Return the number of kilometers that this car has
     *
     * @return This Car's number of kilometers
     */
    public double getKilometres() {
        return kilometres;
    }

    /** Return this Car's ID */
    public int getId() {
        return id;
    }

    /** Returns the string name of the car as defined in constants.EntityStringNames */
    @Override
    public String getStringName() {
        return EntityStringNames.CAR_STRING;
    }

    /** Returns whether this Car is equal to other */
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
                && (this.image.equals(otherCar.image))
                && (this.year == otherCar.year)
                && (this.price == otherCar.price);
    }
}
