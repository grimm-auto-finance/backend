package entityparsers;

import attributes.*;

import javax.json.*;
import java.util.Set;

public class JsonParser implements Parser {

    private final JsonObject jsonObject;

    /**
     * Construct a new JsonParser to parse the given JsonObject
     * @param jsonObject
     */
    public JsonParser(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Parse jsonObject into an AttributeMap with keys and values pulled from jsonObject
     * @return
     */
    public AttributeMap parse() {
        return parseJsonObject(this.jsonObject);
    }

    /**
     * The recursive helper method for parse().
     * This method is recursive since JsonObjects can contain other JsonObjects.
     * @param object
     * @return
     * @throws JsonException
     */
    private AttributeMap parseJsonObject(JsonObject object) throws JsonException {
        AttributeMap map = new AttributeMap();
        Set<String> keys = object.keySet();
        for (String key : keys) {
            JsonValue item = object.get(key);
            Attribute itemAttribute;
            switch (item.getValueType()) {
                case NUMBER:
                    JsonNumber itemNum = (JsonNumber) item;
                    // TODO: figure out how to differentiate ints and doubles better?
                    // isIntegral returns true if the value has .0 at the end even though
                    // it should be a double
//                    if (itemNum.isIntegral()) {
//                        itemAttribute = new IntAttribute(itemNum.intValue());
//                    } else {
//                        itemAttribute = new DoubleAttribute(itemNum.doubleValue());
//                    }
                    itemAttribute = new DoubleAttribute(itemNum.doubleValue());
                    break;
                case STRING:
                    JsonString itemString = (JsonString) item;
                    itemAttribute = new StringAttribute(itemString.getString());
                    break;
                case OBJECT:
                    JsonObject itemObject = item.asJsonObject();
                    itemAttribute = parseJsonObject(itemObject);
                    break;
                default:
                    throw new JsonException("Error in Parsing JsonObject to AttributeMap");
            }
            map.addItem(key, itemAttribute);
        }
        return map;
    }
}
