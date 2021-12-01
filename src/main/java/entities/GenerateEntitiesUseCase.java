// layer: usecases
package entities;

import attributes.ArrayAttribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import java.util.List;
import java.util.Map;

public class GenerateEntitiesUseCase {

    /**
     * Extracts the AttributeMap corresponding to a Car from the given AttributeMap, and constructs
     * that Car
     *
     * @param map an AttributeMap with a submap containing keys and values corresponding to a Car
     * @return a Car constructed using the extracted submap of map
     * @throws Exceptions.FactoryException if the needed submap is not present in map or does not
     *     contain the correct key/value pairs
     */
    public static Car generateCar(AttributeMap map) throws Exceptions.FactoryException {
        AttributeMap carMap = (AttributeMap) map.getItem(EntityStringNames.CAR_STRING);
        return CarFactory.getEntity(carMap);
    }

    /**
     * Extracts the AttributeMap corresponding to a CarBuyer from the given AttributeMap, and
     * constructs that CarBuyer
     *
     * @param map an AttributeMap with a submap containing keys and values corresponding to a
     *     CarBuyer
     * @return a CarBuyer constructed using the extracted submap of map
     * @throws Exceptions.FactoryException if the needed submap is not present in map or does not
     *     contain the correct key/value pairs
     */
    public static CarBuyer generateCarBuyer(AttributeMap map) throws Exceptions.FactoryException {
        AttributeMap buyerMap = (AttributeMap) map.getItem(EntityStringNames.BUYER_STRING);
        return CarBuyerFactory.getEntity(buyerMap);
    }

    /**
     * Extracts the ArrayAttribute corresponding to a list of AddOns from the given AttributeMap,
     * and constructs that List of AddOns
     *
     * @param map an AttributeMap containing an ArrayAttribute, where each value in the array is an
     *     AttributeMap corresponding to an AddOn
     * @return a List of AddOns constructed using the extracted ArrayAttribute of map
     * @throws Exceptions.FactoryException if the needed ArrayAttribute is not present in map or
     *     does not contain the correct key/value pairs
     */
    public static List<AddOn> generateAddOnsFromArray(AttributeMap map)
            throws Exceptions.FactoryException {
        ArrayAttribute addOnArray = (ArrayAttribute) map.getItem(EntityStringNames.ADD_ON_STRING);
        return AddOnFactory.getEntities(addOnArray);
    }

    public static Map<String, AddOn> generateAddOnsFromMap(AttributeMap map)
            throws Exceptions.FactoryException {
        AttributeMap addOnMap = (AttributeMap) map.getItem(EntityStringNames.ADD_ON_STRING);
        return AddOnFactory.getEntities(addOnMap);
    }

    /**
     * Extracts the AttributeMap corresponding to a LoanData from the given AttributeMap, and
     * constructs that LoanData
     *
     * @param map an AttributeMap with a submap containing keys and values corresponding to a
     *     LoanData
     * @return a LoanData constructed using the extracted submap of map
     * @throws Exceptions.FactoryException if the needed submap is not present in map or does not
     *     contain the correct key/value pairs
     */
    public static LoanData generateLoanData(AttributeMap map) throws Exceptions.FactoryException {
        AttributeMap loanMap = (AttributeMap) map.getItem(EntityStringNames.LOAN_STRING);
        return LoanDataFactory.getEntity(loanMap);
    }
}
