package entityparsers;

import entities.AttributeMap;

import javax.json.JsonObject;
import java.util.Map;
import java.util.Set;

public class JsonParser implements Parser {

    private final JsonObject jsonObject;

    public JsonParser(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public AttributeMap parse() {
        AttributeMap entityMap = new AttributeMap();
        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {

        }
    }
}
