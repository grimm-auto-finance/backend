// layer: frameworksanddrivers
package parsing;

import attributes.*;

import constants.Exceptions;

import java.io.InputStream;
import java.util.Set;

import javax.json.*;

/** A parser capable of parsing Attributes from JsonObjects */
public class JsonParser implements Parser {

    private JsonObject jsonObject;

    /**
     * Set the object that this JsonParser will be parsing. This object should be either a
     * JsonObject or InputStream
     *
     * @param obj the object to parse data from
     * @throws Exceptions.ParseException if obj is of invalid type
     */
    public void setParseObject(Object obj) throws Exceptions.ParseException {
        if (obj instanceof JsonObject) {
            setParseObject((JsonObject) obj);
        } else if (obj instanceof InputStream) {
            setParseObject((InputStream) obj);
        } else {
            throw new Exceptions.ParseException("Parse object of invalid type " + obj.getClass());
        }
    }

    /**
     * Sets the object that this JsonParser will be parsing to jsonObject
     *
     * @param jsonObject the given JsonObject parsed to construct JsonParser
     */
    private void setParseObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Sets the object that this JsonParser will be parsing to a JsonObject stored within the given
     * InputStream
     *
     * @param is an InputStream containing a JsonObject
     * @throws Exceptions.ParseException if the InputStream cannot be parsed into a JsonObject
     */
    private void setParseObject(InputStream is) throws Exceptions.ParseException {
        try {
            JsonReader jsonReader = Json.createReader(is);
            this.jsonObject = jsonReader.readObject();
        } catch (JsonException e) {
            throw new Exceptions.ParseException("Failed to parse JsonObject from InputStream", e);
        }
    }

    /**
     * Parse jsonObject into an AttributeMap with keys and values pulled from jsonObject
     *
     * @return The parsed AttributeMap
     */
    public AttributeMap parse() throws Exceptions.ParseException {
        if (this.jsonObject == null) {
            throw new Exceptions.ParseException("can't parse from null jsonobject");
        }
        return parseJsonObject(this.jsonObject);
    }

    /**
     * The recursive helper method for parse(). This method is recursive since JsonObjects can
     * contain other JsonObjects.
     *
     * @param object The JsonObject to be converted to be parsed
     * @return an attribute map representing the JsonObject
     * @throws constants.Exceptions.ParseException if the JsonObject is not a String, Number, or
     *     Object
     */
    private AttributeMap parseJsonObject(JsonObject object) throws Exceptions.ParseException {
        AttributeMap map = new AttributeMap();
        Set<String> keys = object.keySet();
        for (String key : keys) {
            JsonValue item = object.get(key);
            Attribute itemAttribute = parseJsonValue(item);
            map.addItem(key, itemAttribute);
        }
        return map;
    }

    private Attribute parseJsonValue(JsonValue item) throws Exceptions.ParseException {
        Attribute itemAttribute;
        switch (item.getValueType()) {
            case NUMBER:
                JsonNumber itemNum = (JsonNumber) item;
                String numAsString = item.toString();
                if (!numAsString.contains(".")) {
                    itemAttribute = AttributeFactory.createAttribute(itemNum.intValue());
                } else {
                    itemAttribute = AttributeFactory.createAttribute(itemNum.doubleValue());
                }
                break;
            case STRING:
                JsonString itemString = (JsonString) item;
                itemAttribute = AttributeFactory.createAttribute(itemString.getString());
                break;
            case OBJECT:
                JsonObject itemObject = item.asJsonObject();
                itemAttribute = parseJsonObject(itemObject);
                break;
            case ARRAY:
                JsonArray itemArray = item.asJsonArray();
                Attribute[] attributeArray = new Attribute[itemArray.size()];
                for (int i = 0; i < itemArray.size(); i++) {
                    attributeArray[i] = parseJsonValue(itemArray.get(i));
                }
                itemAttribute = AttributeFactory.createAttribute(attributeArray);
                break;
            default:
                throw new Exceptions.ParseException(
                        "Json item of type "
                                + item.getValueType()
                                + " doesn't correspond to any Attribute types");
        }
        return itemAttribute;
    }
}
