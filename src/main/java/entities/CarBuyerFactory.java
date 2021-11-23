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
            budget = (double) map.getItem(EntityStringNames.BUYER_BUDGET).getAttribute();
            creditScore =
                    (int)
                            Math.round(
                                    (Double)
                                            map.getItem(EntityStringNames.BUYER_CREDIT)
                                                    .getAttribute());
            downpayment = (double) map.getItem(EntityStringNames.BUYER_DOWNPAYMENT).getAttribute();
        } catch (ClassCastException | NullPointerException e) {
            String message = "Failed to generate CarBuyer";
            throw new Exceptions.FactoryException(message, e);
        }

        return new CarBuyer(budget, creditScore, downpayment);
    }
}
