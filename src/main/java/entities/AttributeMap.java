package entities;

import java.util.HashMap;
import java.util.Map;

public class AttributeMap extends Attribute {

    private final Map<String, Attribute> items;

    public AttributeMap() {
        items = new HashMap<>();
    }

    public void addItem(String name, Attribute item) {
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

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String key : items.keySet()) {
            builder.append(key).append(": ");
            builder.append(items.get(key));
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        AttributeMap test = new AttributeMap();
        test.addItem("Bruh", new AttributeInt(5));
        test.addItem("String", new AttributeString("hello"));
        test.addItem("double", new AttributeDouble(56.5));
        AttributeMap subMap = new AttributeMap();
        subMap.addItem("sub-string", new AttributeString("substring!"));
        test.addItem("submap", subMap);
        System.out.println(test);
    }
}
