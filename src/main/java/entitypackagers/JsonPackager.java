package entitypackagers;

import attributes.*;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.Map;

public class JsonPackager implements Packager {

    private final AttributeMap packageMap;

    public JsonPackager(AttributeMap packageMap) {
        this.packageMap = packageMap;
    }

    public JsonPackage writePackage() throws Exception {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Map<String, Attribute> map = packageMap.getAttribute();
        for (String key: map.keySet()) {
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
                JsonPackager subPackager = new JsonPackager((AttributeMap) item);
                builder.add(key, subPackager.writePackage().getPackage());
            } else {
                throw new Exception("Unhandled Attribute type");
            }
        }
        return new JsonPackage(builder.build());
    }
}
