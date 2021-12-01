package entities;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddOnFactory {

    /**
     * Construct a new AddOn using the values in the given AttributeMap
     *
     * @param map an AttributeMap containing keys and values corresponding to an AddOn
     * @return a new AddOn constructed from the values in map
     * @throws Exceptions.FactoryException if the required values for AddOn construction aren't
     *     present in map
     */
    public static AddOn getEntity(AttributeMap map) throws Exceptions.FactoryException {
        String name;
        double price;
        String description;
        try {
            name = (String) map.getItem(EntityStringNames.ADD_ON_NAME).getAttribute();
            price = AttributeMap.getDoubleMaybeInteger(EntityStringNames.ADD_ON_PRICE, map);
            description = (String) map.getItem(EntityStringNames.ADD_ON_DESCRIPTION).getAttribute();
        } catch (ClassCastException | NullPointerException e) {
            String message = "Failed to generate AddOn";
            throw new Exceptions.FactoryException(message, e);
        }

        return new AddOn(name, price, description);
    }

    /**
     * Construct a List of AddOns using the values in the given ArrayAttribute
     *
     * @param attArray an ArrayAttribute containing AttributeMaps, each corresponding to an AddOn
     * @return a List of AddOns constructed from the AttributeMaps in attArray
     * @throws Exceptions.FactoryException if the required AttributeMaps aren't present in attArray,
     *     or if those maps do not contain the correct keys and values for AddOn construction
     */
    public static List<AddOn> getEntities(ArrayAttribute attArray)
            throws Exceptions.FactoryException {
        Attribute[] attributes = attArray.getAttribute();
        List<AddOn> addOns = new ArrayList<>();
        for (Attribute a : attributes) {
            try {
                AttributeMap map = (AttributeMap) a;
                addOns.add(getEntity(map));
            } catch (ClassCastException | NullPointerException e) {
                String message = "Failed to generate AddOn List: ";
                throw new Exceptions.FactoryException(message, e);
            }
        }
        return addOns;
    }

    public static Map<String, AddOn> getEntities(AttributeMap map)
            throws Exceptions.FactoryException {
        Map<String, AddOn> addOns = new HashMap<>();
        for (String name : map.getAttribute().keySet()) {
            try {
                addOns.put(name, getEntity((AttributeMap) map.getItem(name)));
            } catch (ClassCastException | NullPointerException e) {
                String message = "Failed to generate AddOn List: ";
                throw new Exceptions.FactoryException(message, e);
            }
        }
        return addOns;
    }
}
