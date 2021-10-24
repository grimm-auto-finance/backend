package entitypackagers;

import constants.EntityStringNames;
import entities.CarBuyer;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.swing.text.html.parser.Entity;

public class PackageCarBuyerUseCase {
    private final JsonObjectBuilder completeJsonBuilder;
    private final JsonObjectBuilder thisJsonBuilder;

    /**
     * Constructs a new PackageCarBuyerUseCase that writes CarBuyer information to the given
     * JsonObjectBuilder
     *
     * @param jsonBuilder the JsonObjectBuilder to write CarBuyer information to
     */
    public PackageCarBuyerUseCase(JsonObjectBuilder jsonBuilder) {
        this.completeJsonBuilder = jsonBuilder;
        this.thisJsonBuilder = Json.createObjectBuilder();
    }

    /**
     * Write the given CarBuyer's data to completeJsonBuilder
     *
     * @param carBuyer the CarBuyer to serialize
     */
    public void writeEntity(CarBuyer carBuyer) {
        thisJsonBuilder.add(EntityStringNames.BUYER_BUDGET, carBuyer.getBudget());
        thisJsonBuilder.add(EntityStringNames.BUYER_CREDIT, carBuyer.getCreditScore());
        completeJsonBuilder.add(EntityStringNames.BUYER_STRING, thisJsonBuilder);
    }
}
