package entitypackagers;

import entities.AddOn;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class PackageAddOnUseCase {
    private final JsonObjectBuilder completeJsonBuilder;
    private final JsonObjectBuilder thisJsonBuilder;

    /**
     * Constructs a new PackageAddOnUseCase that writes AddOn information to the given
     * JsonObjectBuilder
     *
     * @param jsonBuilder the JsonObjectBuilder to write AddOn information to
     */
    public PackageAddOnUseCase(JsonObjectBuilder jsonBuilder) {
        this.completeJsonBuilder = jsonBuilder;
        this.thisJsonBuilder = Json.createObjectBuilder();
    }

    /**
     * Write the given AddOn's data to completeJsonBuilder
     *
     * @param addOn the AddOn to serialize
     */
    public void writeEntity(AddOn addOn) {
        thisJsonBuilder.add("name", addOn.getName());
        thisJsonBuilder.add("price", addOn.getPrice());
        thisJsonBuilder.add("description", addOn.getDescription());
        completeJsonBuilder.add(addOn.getName() + " add-on", thisJsonBuilder);
    }
}
