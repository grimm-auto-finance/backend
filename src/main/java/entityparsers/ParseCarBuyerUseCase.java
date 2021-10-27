package entityparsers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.CarBuyer;

import entitybuilder.GenerateBuyerUseCase;

import javax.json.JsonObject;

public class ParseCarBuyerUseCase {

    private final AttributeMap map;

    /**
     * Constructs a new ParseCarBuyerUseCase to create a CarBuyer using the given JsonObject
     *
     * @param parser
     */
    public ParseCarBuyerUseCase(Parser parser) {
        this.map = parser.parse();
    }

    /**
     * Creates a CarBuyer object from the fields in jsonObject
     *
     * @return
     */
    public CarBuyer parse() throws ClassCastException {
        GenerateBuyerUseCase buyerGenerator = new GenerateBuyerUseCase();
        AttributeMap buyerMap = (AttributeMap) map.getItem(EntityStringNames.BUYER_STRING);
        double budget = (double) buyerMap.getItem("pytBudget").getAttribute();
        int creditScore = (int) buyerMap.getItem("creditScore").getAttribute();
        return buyerGenerator.GenerateBuyerDataUseCase(budget, creditScore);
    }
}
