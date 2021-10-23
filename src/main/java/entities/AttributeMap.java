package entities;

import java.util.HashMap;
import java.util.Map;

public class AttributeMap extends Attribute {

    private final Map<String, Attribute> items;

    public AttributeMap() {
        items = new HashMap<>();
    }

    public void addItem(String name, AttributeMap item) {
        items.put(name, item);
    }

    public Attribute getItem(String name) {
        if (!items.containsKey(name)) {
            throw new NullPointerException("No item in map with this name");
        }
        return items.get(name);
    }

    public Map<String, Attribute> getAttribute() {
        return items;
    }
}
