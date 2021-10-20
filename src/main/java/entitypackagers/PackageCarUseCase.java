package entitypackagers;

import entities.Car;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class PackageCarUseCase {
    private final JsonObjectBuilder completeJsonBuilder;
    private final JsonObjectBuilder thisJsonBuilder;

    /**
     * Constructs a new PackageCarBuyerUseCase that writes
     * CarBuyer information to the given JsonObjectBuilder
     * @param jsonBuilder the JsonObjectBuilder to write CarBuyer information to
     */
    public PackageCarUseCase(JsonObjectBuilder jsonBuilder) {
        this.completeJsonBuilder = jsonBuilder;
        this.thisJsonBuilder = Json.createObjectBuilder();
    }

    /**
     * Write the given Car's data to completeJsonBuilder
     * @param car the Car to serialize
     */
    public void writeEntity(Car car) {
        thisJsonBuilder.add("price", car.getPrice());
        thisJsonBuilder.add("name", car.getName());
        thisJsonBuilder.add("year", car.getYear());
        // TODO: create AddOn Packager to call here
        completeJsonBuilder.add("car buyer", thisJsonBuilder);
    }
}
