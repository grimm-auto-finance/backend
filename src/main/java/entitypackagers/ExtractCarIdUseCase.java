package entitypackagers;

import attributes.AttributeMap;
import attributes.IntAttribute;

import constants.EntityStringNames;
import constants.Exceptions;

public class ExtractCarIdUseCase {
    /**
     * Extracts the AttributeMap corresponding to an id from the given AttributeMap, and returns
     * that id
     *
     * @param map an AttributeMap with a submap containing key and value corresponding to an id
     * @return an id constructed using the extracted submap of map
     */
    public static int extractId(AttributeMap map) throws Exceptions.FactoryException {
        int id;
        try {
            id = (int) map.getItem(EntityStringNames.ID_STRING).getAttribute();
        } catch (ClassCastException | NullPointerException e) {
            String message = "Failed to extract car id";
            throw new Exceptions.FactoryException(message, e);
        }
        return id;
    }
}
