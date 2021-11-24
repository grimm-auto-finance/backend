package entities;

import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import java.util.Map;

public class CarFactory {

    /**
     * Constructs a new Car using the values in the given AttributeMap If the given map contains an
     * ArrayAttribute with the correct string name, will also initialize the Car's stored AddOns
     *
     * @param map an AttributeMap containing keys and values corresponding to the attributes of a
     *     Car
     * @return a Car constructed from the values in map
     * @throws Exceptions.FactoryException if the required key/value pairs for Car construction are
     *     not present in map
     */
    public static Car getEntity(AttributeMap map) throws Exceptions.FactoryException {
        String make, model;
        int year;
        double price;
        Map<String, AddOn> addOnMap;
        double kilometres;
        int id;
        try {
            make = (String) map.getItem(EntityStringNames.CAR_MAKE).getAttribute();
            model = (String) map.getItem(EntityStringNames.CAR_MODEL).getAttribute();
            year =
                    (int)
                            Math.round(
                                    (Double)
                                            map.getItem(EntityStringNames.CAR_YEAR).getAttribute());
            price = (double) map.getItem(EntityStringNames.CAR_PRICE).getAttribute();
            addOnMap = GenerateEntitiesUseCase.generateAddOnsFromMap(map);
            kilometres = (double) map.getItem(EntityStringNames.CAR_KILOMETRES).getAttribute();
            id = (int) map.getItem(EntityStringNames.CAR_ID).getAttribute();
        } catch (ClassCastException | NullPointerException e) {
            String message = "Failed to generate Car: ";
            Exceptions.FactoryException ex =
                    new Exceptions.FactoryException(message + '\n' + e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        return new Car(kilometres, price, make, model, year, addOnMap, id);
    }
    private static int doubleToInt(Double doub){
        return (int) Math.round(doub);
    }
}
