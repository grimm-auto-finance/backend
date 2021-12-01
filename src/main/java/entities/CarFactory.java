// Layer: usecases
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
            year = (int) map.getItem(EntityStringNames.CAR_YEAR).getAttribute();
            price = AttributeMap.getDoubleMaybeInteger(EntityStringNames.CAR_PRICE, map);
            addOnMap = GenerateEntitiesUseCase.generateAddOnsFromMap(map);
            kilometres = AttributeMap.getDoubleMaybeInteger(EntityStringNames.CAR_KILOMETRES, map);
            id = (int) map.getItem(EntityStringNames.CAR_ID).getAttribute();
        } catch (ClassCastException | NullPointerException e) {
            String message = "Failed to generate Car from map: " + map.toString();
            throw new Exceptions.FactoryException(message, e);
        }
        return new Car(kilometres, price, make, model, year, addOnMap, id);
    }
}
