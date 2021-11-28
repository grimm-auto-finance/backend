package entities;

import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

public class CarBuyerFactory {

    /**
     * Constructs a new CarBuyer using the values in the given AttributeMap
     *
     * @param map an AttributeMap containing keys and values corresponding to the attributes of a
     *     CarBuyer
     * @return a CarBuyer constructed from the values in map
     * @throws Exceptions.FactoryException if the required key/value pairs for CarBuyer construction
     *     aren't present in map
     */
    public static CarBuyer getEntity(AttributeMap map) throws Exceptions.FactoryException {
        double budget;
        int creditScore;
        double downpayment;
        try {
            budget = AttributeMap.getDoubleMaybeInteger(EntityStringNames.BUYER_BUDGET, map);
            creditScore = (int) map.getItem(EntityStringNames.BUYER_CREDIT).getAttribute();
            downpayment =
                    AttributeMap.getDoubleMaybeInteger(EntityStringNames.BUYER_DOWNPAYMENT, map);
        } catch (ClassCastException | NullPointerException e) {
            String message = "Failed to generate CarBuyer";
            throw new Exceptions.FactoryException(message, e);
        }

        return new CarBuyer(budget, creditScore, downpayment);
    }
}
