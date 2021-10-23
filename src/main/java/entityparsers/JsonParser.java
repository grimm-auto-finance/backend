package entityparsers;

import attributes.*;

import javax.json.*;
import java.util.Set;

public class JsonParser implements Parser {

    private final JsonObject jsonObject;

    public JsonParser(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public AttributeMap parse() {
        return parseJsonObject(this.jsonObject);
    }

    public AttributeMap parseJsonObject(JsonObject object) throws JsonException {
        AttributeMap map = new AttributeMap();
        Set<String> keys = object.keySet();
        for (String key : keys) {
            JsonValue item = object.get(key);
            Attribute itemAttribute;
            switch (item.getValueType()) {
                case NUMBER:
                    JsonNumber itemNum = (JsonNumber) item;
                    if (itemNum.isIntegral()) {
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
                default:
                    throw new JsonException("Error in Parsing JsonObject to AttributeMap");
            }
            map.addItem(key, itemAttribute);
        }
        return map;
    }
}
