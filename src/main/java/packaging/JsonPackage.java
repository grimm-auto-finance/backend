// layer: frameworksanddrivers
package packaging;

import javax.json.JsonValue;

public class JsonPackage extends Package {

    private final JsonValue val;

    /**
     * Constructs a new JsonPackage to contain the given JsonValue
     *
     * @param val The jsonValue to be stored
     */
    public JsonPackage(JsonValue val) {
        this.val = val;
    }

    /**
     * Get this package's contents
     *
     * @return the JsonValue stored in this JsonPackage
     */
    public JsonValue getPackage() {
        return val;
    }
}
