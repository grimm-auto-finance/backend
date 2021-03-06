// layer: ignore
package attributes;

import java.util.HashMap;
import java.util.Map;

/** An Attribute representing a Map between String names and Attributes */
public class AttributeMap extends Attribute {

    // A map between attribute names and attributes
    private final Map<String, Attribute> items;

    /** Constructs a new, empty AttributeMap */
    public AttributeMap() {
        items = new HashMap<>();
    }

    /**
     * Adds the given item Object to the map with the given name
     *
     * @param name The name of the attribute created by the attributized Object
     * @param item the Object to be attributized and added to the map
     * @throws ClassCastException This is because an incorrect class is used for the parameter
     */
    public void addItem(String name, Object item) throws ClassCastException {
        this.addItem(name, AttributeFactory.createAttribute(item));
    }

    /**
     * Adds the given item (Attribute) to the map with the given name
     *
     * @param name The name of the Attribute to be added to the map
     * @param item The Attribute to be added to the map
     * @throws NullPointerException if item is null
     */
    public void addItem(String name, Attribute item) throws NullPointerException {
        items.put(name, item);
    }

    /**
     * Returns the item with the given name from this map If no item in the map exists with this
     * name, throws NullPointerException
     *
     * @param name The key corresponding to name of the Attribute
     * @return The item corresponding to the key in the map
     * @throws NullPointerException if no item exists in the map with the given name
     */
    public Attribute getItem(String name) throws NullPointerException {
        if (!items.containsKey(name)) {
            throw new NullPointerException("No item in map with name " + name);
        }
        return items.get(name);
    }

    /**
     * Returns the map between attribute names and Attributes stored in this AttributeMap
     *
     * @return The Attribute map
     */
    public Map<String, Attribute> getAttribute() {
        return items;
    }

    public static AttributeMap combine(AttributeMap first, AttributeMap second) {
        Map<String, Attribute> firstItems = first.getAttribute();
        Map<String, Attribute> secondItems = second.getAttribute();
        AttributeMap combined = new AttributeMap();
        for (String s : firstItems.keySet()) {
            combined.addItem(s, firstItems.get(s));
        }
        for (String s : secondItems.keySet()) {
            combined.addItem(s, secondItems.get(s));
        }
        return combined;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    /**
     * Get a double value from the map that might be stored as an integer Basically, sometimes
     * values get parsed into AttributeMaps as an Integer even though we need them to be Doubles. So
     * this lets us retrieve them as Doubles regardless.
     */
    public static double getDoubleMaybeInteger(String key, AttributeMap map) {
        Object item = map.getItem(key).getAttribute();
        if (item instanceof Integer) {
            return ((Integer) item).doubleValue();
        } else {
            return (double) item;
        }
    }
}
