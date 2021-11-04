package entities;

import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;

public class AddOnFactory {

    public static AddOn getEntity(AttributeMap map) throws Exceptions.FactoryException {
        String name;
        double price;
        String description;
        try {
            name = (String) map.getItem(EntityStringNames.ADD_ON_NAME).getAttribute();
            price = (double) map.getItem(EntityStringNames.ADD_ON_PRICE).getAttribute();
            description = (String) map.getItem(EntityStringNames.ADD_ON_DESCRIPTION).getAttribute();
        } catch (ClassCastException | NullPointerException e) {
            Exceptions.FactoryException ex = new Exceptions.FactoryException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }

        return new AddOn(name, price, description);
    }
}
