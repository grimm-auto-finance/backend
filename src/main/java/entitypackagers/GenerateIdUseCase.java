package entitypackagers;

import attributes.AttributeMap;
import attributes.DoubleAttribute;

import constants.EntityStringNames;

public class GenerateIdUseCase {
    /**
     * Extracts the AttributeMap corresponding to an id from the given AttributeMap, and returns
     * that id
     *
     * @param map an AttributeMap with a submap containing key and value corresponding to an id
     * @return an id constructed using the extracted submap of map
     */
    public static int generateId(AttributeMap map) {
        DoubleAttribute idAttribute = (DoubleAttribute) map.getItem(EntityStringNames.ID_STRING);
        return idAttribute.getAttribute().intValue();
    }
}
