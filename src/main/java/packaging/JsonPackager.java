// frameworksanddrivers
package packaging;

import attributes.*;

import constants.Exceptions;

import java.util.Map;

import javax.json.*;

/** A packager for converting Attributes to JsonPackages */
public class JsonPackager implements Packager {

    /**
     * Writes packageMap to a JsonObject
     *
     * @param item The Attribute from which the JsonObject is created
     * @return a JsonPackage containing the JsonObject with item's data
     * @throws Exceptions.PackageException if item of unknown type
     */
    public JsonPackage writePackage(Attribute item) throws Exceptions.PackageException {
        if (item instanceof AttributeMap) {
            return writePackageFromMap((AttributeMap) item);
        } else if (item instanceof ArrayAttribute) {
            return writePackageFromArray((ArrayAttribute) item);
        } else if (item instanceof StringAttribute) {
            return new JsonPackage(Json.createValue((String) item.getAttribute()));
        } else if (item instanceof DoubleAttribute) {
            return new JsonPackage(Json.createValue((double) item.getAttribute()));
        } else if (item instanceof IntAttribute) {
            return new JsonPackage(Json.createValue((int) item.getAttribute()));
        } else {
            throw new Exceptions.PackageException("cannot package unhandled attribute type");
        }
    }

    private JsonPackage writePackageFromMap(AttributeMap packageMap)
            throws Exceptions.PackageException {
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
            } else if (item instanceof ArrayAttribute) {
                JsonArray itemArray = getJsonArray((ArrayAttribute) item);
                builder.add(key, itemArray);
            } else {
                throw new Exceptions.PackageException(
                        "Unhandled Attribute type " + item.getClass());
            }
        }
        return new JsonPackage(builder.build());
    }

    private JsonPackage writePackageFromArray(ArrayAttribute packageArray)
            throws Exceptions.PackageException {
        JsonArray array = getJsonArray(packageArray);
        return new JsonPackage(array);
    }

    /**
     * Takes in an ArrayAttribute and coverts it into a JsonArray
     *
     * @param item The ArrayAttribute to be used to create a JsonArray
     * @return The JsonArray
     * @throws Exceptions.PackageException Returns an exception if an item cannot be converted to a
     *     Json
     */
    private JsonArray getJsonArray(ArrayAttribute item) throws Exceptions.PackageException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Attribute a : item.getAttribute()) {
            if (a instanceof IntAttribute) {
                arrayBuilder.add((int) a.getAttribute());
            } else if (a instanceof DoubleAttribute) {
                arrayBuilder.add((double) a.getAttribute());
            } else if (a instanceof StringAttribute) {
                arrayBuilder.add((String) a.getAttribute());
            } else if (a instanceof AttributeMap) {
                JsonPackager subPackager = new JsonPackager();
                arrayBuilder.add(subPackager.writePackage((AttributeMap) a).getPackage());
            } else if (a instanceof ArrayAttribute) {
                JsonArray subArray = getJsonArray((ArrayAttribute) a);
                arrayBuilder.add(subArray);
            }
        }
        return arrayBuilder.build();
    }
}
