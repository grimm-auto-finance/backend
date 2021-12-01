// Layer: frameworksanddrivers
package entitypackagers;

import javax.json.JsonObject;

public class JsonPackage extends Package {

    private final JsonObject obj;

    /**
     * Constructs a new JsonPackage to contain the given JsonObject
     *
     * @param obj The jsonObject
     */
    public JsonPackage(JsonObject obj) {
        this.obj = obj;
    }

    /**
     * Get this package's contents
     *
     * @return the JsonObject stored in this JsonPackage
     */
    public JsonObject getPackage() {
        return obj;
    }
}
