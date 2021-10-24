package entitypackagers;

import constants.EntityStringNames;
import entities.AddOn;
import entities.Car;

import java.util.Map;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.swing.text.html.parser.Entity;

public class PackageCarUseCase {
    private final JsonObjectBuilder completeJsonBuilder;
    private final JsonObjectBuilder thisJsonBuilder;

    /**
     * Constructs a new PackageCarBuyerUseCase that writes CarBuyer information to the given
     * JsonObjectBuilder
     *
     * @param jsonBuilder the JsonObjectBuilder to write CarBuyer information to
     */
    public PackageCarUseCase(JsonObjectBuilder jsonBuilder) {
        this.completeJsonBuilder = jsonBuilder;
        this.thisJsonBuilder = Json.createObjectBuilder();
    }

    /**
     * Write the given Car's data to completeJsonBuilder
     *
     * @param car the Car to serialize
     */
    public void writeEntity(Car car) {
        thisJsonBuilder.add(EntityStringNames.CAR_PRICE, car.getPrice());
        thisJsonBuilder.add(EntityStringNames.CAR_MAKE, car.getMake());
        thisJsonBuilder.add(EntityStringNames.CAR_MODEL, car.getModel());
        thisJsonBuilder.add(EntityStringNames.CAR_YEAR, car.getYear());

        // create a new JsonObjectBuilder to handle serializing this car's add-ons
        // this ensures that the add-ons are listed together in a sub-entry of the Json object
        JsonObjectBuilder addOnJsonBuilder = Json.createObjectBuilder();
        PackageAddOnUseCase addOnPackager = new PackageAddOnUseCase(addOnJsonBuilder);
        Map<String, AddOn> addOns = car.getAddOns();
        for (String addOnName : addOns.keySet()) {
            addOnPackager.writeEntity(addOns.get(addOnName));
        }

        thisJsonBuilder.add("add-ons", addOnJsonBuilder);
        completeJsonBuilder.add(EntityStringNames.CAR_STRING, thisJsonBuilder);
    }
}
