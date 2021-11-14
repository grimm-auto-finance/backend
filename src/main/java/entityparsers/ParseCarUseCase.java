package entityparsers;

import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.Car;

import entitybuilder.GenerateCarUseCase;

public class ParseCarUseCase {
    private final AttributeMap map;

    /**
     * Constructs a new ParseCarUseCase to create a Car using the given Parser
     *
     * @param parser object to be parsed
     */
    public ParseCarUseCase(Parser parser) throws Exceptions.ParseException {
        this.map = parser.parse();
    }

    /**
     * Creates a Car object from the map
     *
     * @return Car
     */
    public Car parse() throws Exceptions.ParseException {
        GenerateCarUseCase carGenerator = new GenerateCarUseCase();
        String make, model;
        int year;
        double price;
        double kilometres;
        try {
            AttributeMap carMap = (AttributeMap) map.getItem(EntityStringNames.CAR_STRING);
            make = (String) carMap.getItem(EntityStringNames.CAR_MAKE).getAttribute();
            model = (String) carMap.getItem(EntityStringNames.CAR_MODEL).getAttribute();
            year = (int) carMap.getItem(EntityStringNames.CAR_YEAR).getAttribute();
            price = (double) carMap.getItem(EntityStringNames.CAR_PRICE).getAttribute();
            kilometres = (double) carMap.getItem(EntityStringNames.CAR_KILOMETRES).getAttribute();
        } catch (ClassCastException | NullPointerException e) {
            Exceptions.ParseException ex = new Exceptions.ParseException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        // TODO: Support optional inclusion of addons?
        return carGenerator.GenerateCarUseCase(kilometres, price, make, model, year);
    }
}
