package entityparsers;

import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;
import constants.Exceptions;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.util.Set;

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
     * Parse jsonObject into an AttributeMap with keys and values pulled from jsonObject
     *
     * @return AttributeMap
     */
    public AttributeMap parse() throws Exceptions.ParseException {
        return parseJsonObject(this.jsonObject);
    }

    /**
     * The recursive helper method for parse(). This method is recursive since JsonObjects can
     * contain other JsonObjects.
     *
     * @param object the given JsonObject used to create the AttributeMap
     * @return AttributeMap
     * @throws Exceptions.ParseException if the JsonObject is not a String, Number, or Object
     */
    private AttributeMap parseJsonObject(JsonObject object) throws Exceptions.ParseException {
        AttributeMap map = new AttributeMap();
        Set<String> keys = object.keySet();
        for (String key : keys) {
            JsonValue item = object.get(key);
            Attribute itemAttribute;
            switch (item.getValueType()) {
                case NUMBER:
                    JsonNumber itemNum = (JsonNumber) item;
                    String tempString = itemNum.toString();
                    if (tempString.contains(".")) {
                        itemAttribute = AttributeFactory.getAttribute(itemNum.doubleValue());
                    } else {
                        itemAttribute = AttributeFactory.getAttribute(itemNum.intValue());
                    }
                    break;
                case STRING:
                    JsonString itemString = (JsonString) item;
                    itemAttribute = AttributeFactory.getAttribute(itemString.getString());
                    break;
                case OBJECT:
                    JsonObject itemObject = item.asJsonObject();
                    itemAttribute = parseJsonObject(itemObject);
                    break;
                default:
                    throw new Exceptions.ParseException(
                            "Json item doesn't correspond to any Attribute types");
            }
            map.addItem(key, itemAttribute);
        }
        return map;
    }
}
