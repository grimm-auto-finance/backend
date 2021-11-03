package attributes;

import java.util.HashMap;
import java.util.Map;

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
        this.addItem(name, AttributeFactory.getAttribute(item));
    }

    /**
     * Adds the given item (Attribute) to the map with the given name
     *
     * @param name The name of the Attribute to be added to the map
     * @param item The Attribute to be added to the map
     */
    public void addItem(String name, Attribute item) {
        items.put(name, item);
    }

    /**
     * Returns the item with the given name from this map If no item in the map exists with this
     * name, throws NullPointerException
     *
     * @param name The key corresponding to name of the Attribute
     * @return
     * @throws NullPointerException if no item exists in the map with the given name
     */
    public Attribute getItem(String name) throws NullPointerException {
        if (!items.containsKey(name)) {
            throw new NullPointerException("No item in map with this name");
        }
        return items.get(name);
    }

    /**
     * Returns the map between attribute names and Attributes stored in this AttributeMap
     *
     * @return
     */
    public Map<String, Attribute> getAttribute() {
        return items;
    }
}
