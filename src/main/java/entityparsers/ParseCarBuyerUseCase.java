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
     * @param parser
     */
    public ParseCarBuyerUseCase(Parser parser) throws Exceptions.ParseException {
        this.map = parser.parse();
    }

    /**
     * Creates a CarBuyer object from the fields in jsonObject
     *
     * @return
     */
    public CarBuyer parse() throws Exceptions.ParseException {
        GenerateBuyerUseCase buyerGenerator = new GenerateBuyerUseCase();
        double budget;
        int creditScore;
        try {
            // TODO: should throw an exception if any of these are null
            AttributeMap buyerMap = (AttributeMap) map.getItem(EntityStringNames.BUYER_STRING);
            budget = (double) buyerMap.getItem(EntityStringNames.BUYER_BUDGET).getAttribute();
            creditScore =
                    (int)
                            Math.round(
                                    (Double)
                                            buyerMap.getItem(EntityStringNames.BUYER_CREDIT)
                                                    .getAttribute());
        } catch (ClassCastException e) {
            Exceptions.ParseException ex = new Exceptions.ParseException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }

        return buyerGenerator.GenerateBuyerDataUseCase(budget, creditScore);
    }
}
