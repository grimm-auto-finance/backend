package entityparsers;
import entities.CarBuyer;
import entitybuilder.GenerateBuyerUseCase;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ParseCarBuyerUseCase {

    private final JsonObject jsonObject;

    /**
     * Constructs a new ParseCarBuyerUseCase to create a CarBuyer
     * using the given JsonObject
     * @param jsonObject
     */
    public ParseCarBuyerUseCase(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Creates a CarBuyer object from the fields in jsonObject
     * @return
     */
    public CarBuyer parse() throws NullPointerException {
        GenerateBuyerUseCase buyerGenerator = new GenerateBuyerUseCase();

        JsonObject buyerObj = jsonObject.getJsonObject("car buyer");
        double budget = buyerObj.getJsonNumber("pytBudget").doubleValue();
        int creditScore = buyerObj.getJsonNumber("creditScore").intValue();
        return buyerGenerator.GenerateBuyerDataUseCase(budget, creditScore);
    }
}
