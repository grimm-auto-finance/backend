package entityparsers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.Car;

import entitybuilder.GenerateCarUseCase;

import javax.json.JsonObject;
import javax.swing.text.html.parser.Entity;

public class ParseCarUseCase {
    private final AttributeMap map;

    /**
     * Constructs a new ParseCarUseCase to create a Car using the given Parser
     *
     * @param parser
     */
    public ParseCarUseCase(Parser parser) {
        this.map = parser.parse();
    }

    /**
     * Creates a Car object from the fields in jsonObject
     *
     * @return
     */
    public Car parse() throws ClassCastException {
        GenerateCarUseCase carGenerator = new GenerateCarUseCase();
        AttributeMap carMap = (AttributeMap) map.getItem(EntityStringNames.CAR_STRING);
        String make = (String) carMap.getItem(EntityStringNames.CAR_MAKE).getAttribute();
        String model = (String) carMap.getItem(EntityStringNames.CAR_MODEL).getAttribute();
        int year = (int) carMap.getItem(EntityStringNames.CAR_YEAR).getAttribute();
        int price = (int) carMap.getItem(EntityStringNames.CAR_PRICE).getAttribute();

        // TODO: Support optional inclusion of addons?
        return carGenerator.GenerateCarUseCase(price, make, model, year);
    }
}
