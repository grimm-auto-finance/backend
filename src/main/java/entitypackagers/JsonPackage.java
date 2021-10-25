package entitypackagers;

import javax.json.JsonObject;

public class JsonPackage extends Package {

    private final JsonObject obj;

    public JsonPackage(JsonObject obj) {
        this.obj = obj;
    }

    public JsonObject getPackage() {
        return obj;
    }
}
