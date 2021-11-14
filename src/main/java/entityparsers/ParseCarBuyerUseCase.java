package entityparsers;

import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.CarBuyer;

import entitybuilder.GenerateBuyerUseCase;

public class ParseCarBuyerUseCase {

    private final AttributeMap map;

    /**
     * Constructs a new ParseCarBuyerUseCase to create a CarBuyer using the given JsonObject
     *
     * @param parser The parser object to be parsed
     */
    public ParseCarBuyerUseCase(Parser parser) throws Exceptions.ParseException {
        this.map = parser.parse();
    }

    /**
     * Creates a CarBuyer object from the map
     *
     * @throws Exceptions.ParseException if invalid map classes
     * @return CarBuyer
     */
    public CarBuyer parse() throws Exceptions.ParseException {
        GenerateBuyerUseCase buyerGenerator = new GenerateBuyerUseCase();
        double budget;
        int creditScore;
        try {
            // TODO: decide whether these should expect a complete attribute map (with multiple
            // objects) or just the buyer part (where does the extraction occur?)
            AttributeMap buyerMap = (AttributeMap) map.getItem(EntityStringNames.BUYER_STRING);
            budget = (double) buyerMap.getItem(EntityStringNames.BUYER_BUDGET).getAttribute();
            creditScore =
                    (int)
                            Math.round(
                                    (Double)
                                            buyerMap.getItem(EntityStringNames.BUYER_CREDIT)
                                                    .getAttribute());
        } catch (ClassCastException | NullPointerException e) {
            Exceptions.ParseException ex = new Exceptions.ParseException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }

        return buyerGenerator.GenerateBuyerDataUseCase(budget, creditScore);
    }
}
