package entityparsers;

import attributes.*;

import constants.Exceptions;

import java.io.InputStream;
import java.util.Set;

import javax.json.*;

public class JsonParser implements Parser {

    private final JsonObject jsonObject;

    /**
     * Construct a new JsonParser
     *
     * @param jsonObject the given JsonObject parsed to construct JsonParser
     */
    public JsonParser(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Constructs a new JsonParser by retrieving a JsonObject from the given InputStream
     *
     * @param is an InputStream containing a JsonObject
     * @throws Exceptions.ParseException if the retrieval fails
     */
    public JsonParser(InputStream is) throws Exceptions.ParseException {
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
                    itemAttribute = new IntAttribute(itemNum.intValue());
                } else {
                    itemAttribute = new DoubleAttribute(itemNum.doubleValue());
                }
                break;
            case STRING:
                JsonString itemString = (JsonString) item;
                itemAttribute = new StringAttribute(itemString.getString());
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
                itemAttribute = new ArrayAttribute(attributeArray);
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
