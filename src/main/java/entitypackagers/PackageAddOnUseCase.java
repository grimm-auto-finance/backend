package entitypackagers;

import constants.EntityStringNames;
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
        thisJsonBuilder.add(EntityStringNames.ADD_ON_NAME, addOn.getName());
        thisJsonBuilder.add(EntityStringNames.ADD_ON_PRICE, addOn.getPrice());
        thisJsonBuilder.add(EntityStringNames.ADD_ON_DESCRIPTION, addOn.getDescription());
        completeJsonBuilder.add(EntityStringNames.ADD_ON_STRING + ": " + addOn.getName(), thisJsonBuilder);
    }
}
