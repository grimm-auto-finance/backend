package entities;

import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;

public class GenerateEntitiesUseCase {

    public static Car generateCar(AttributeMap map) throws Exceptions.FactoryException {
        AttributeMap carMap = (AttributeMap) map.getItem(EntityStringNames.CAR_STRING);
        return CarFactory.getEntity(carMap);
    }

    public static CarBuyer generateCarBuyer(AttributeMap map) throws Exceptions.FactoryException {
        AttributeMap buyerMap = (AttributeMap) map.getItem(EntityStringNames.BUYER_STRING);
        return CarBuyerFactory.getEntity(buyerMap);
    }

    public static AddOn generateAddOn(AttributeMap map) throws Exceptions.FactoryException {
        AttributeMap addOnMap = (AttributeMap) map.getItem(EntityStringNames.ADD_ON_STRING);
        return AddOnFactory.getEntity(addOnMap);
    }

    public static LoanData generateLoanData(AttributeMap map) throws Exceptions.FactoryException {
        AttributeMap loanMap = (AttributeMap) map.getItem(EntityStringNames.LOAN_STRING);
        return LoanDataFactory.getEntity(loanMap);
    }
}
