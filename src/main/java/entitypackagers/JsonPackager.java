package entitypackagers;

import attributes.*;

import constants.Exceptions;

import java.util.Map;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class JsonPackager implements Packager {

    /**
     * Writes packageMap to a JsonObject'
     *
     * @param packageMap The AttributeMap from which the JsonObject is created
     * @return a JsonPackage containing the JsonObject with packageMap's data
     * @throws Exceptions.PackageException if an item in the AttributeMap is of unknown type
     */
    public JsonPackage writePackage(AttributeMap packageMap) throws Exceptions.PackageException {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Map<String, Attribute> map = packageMap.getAttribute();
        for (String key : map.keySet()) {
            Attribute item = map.get(key);
            if (item instanceof IntAttribute) {
                int itemInt = (int) item.getAttribute();
                builder.add(key, itemInt);
            } else if (item instanceof DoubleAttribute) {
                double itemDouble = (double) item.getAttribute();
                builder.add(key, itemDouble);
            } else if (item instanceof StringAttribute) {
                String itemString = (String) item.getAttribute();
                builder.add(key, itemString);
            } else if (item instanceof AttributeMap) {
                JsonPackager subPackager = new JsonPackager();
                builder.add(key, subPackager.writePackage((AttributeMap) item).getPackage());
            } else {
                throw new Exceptions.PackageException("Unhandled Attribute type");
            }
        }
        return new JsonPackage(builder.build());
    }
}
