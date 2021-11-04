package entities;

import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;
import entitybuilder.GenerateBuyerUseCase;

public class CarBuyerFactory {

    public static CarBuyer getEntity(AttributeMap map) throws Exceptions.FactoryException {
        double budget;
        int creditScore;
        try {
            budget = (double) map.getItem(EntityStringNames.BUYER_BUDGET).getAttribute();
            creditScore =
                    (int)
                            Math.round(
                                    (Double)
                                            map.getItem(EntityStringNames.BUYER_CREDIT)
                                                    .getAttribute());
        } catch (ClassCastException | NullPointerException e) {
            Exceptions.FactoryException ex = new Exceptions.FactoryException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }

        return new CarBuyer(budget, creditScore);
    }
}
