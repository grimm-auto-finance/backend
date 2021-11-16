package entities;

import constants.EntityStringNames;

/** A class to represent an individual add-on that can be added to a car. */
public class AddOn extends Entity {
    private final String name;
    private double price;
    private String description;

    /**
     * Constructs a new AddOn objects with the given name, price, and description.
     *
     * @param name The name of the addon
     * @param price The price of the addon
     * @param description A string describing the addon and its use
     */
    protected AddOn(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    /**
     * Returns the name of this add-on
     *
     * @return The name of the addon
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of this AddOn
     *
     * @return The price of the addon
     */
    public double getPrice() {
        return price;
    }

    /**
     * Updates the price of this AddOn
     *
     * @param price The price of the addon
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the description of this AddOn
     *
     * @return The description of the addon
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description of this AddOn
     *
     * @param description The description of the addon
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getStringName() {
        return EntityStringNames.ADD_ON_STRING;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AddOn)) {
            return false;
        }
        AddOn otherAddOn = (AddOn) other;
        return (this.name.equals(otherAddOn.name))
                && (this.price == otherAddOn.price)
                && (this.description.equals(otherAddOn.description));
    }
}
