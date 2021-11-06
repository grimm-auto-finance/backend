package entities;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;

import java.util.ArrayList;
import java.util.List;

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

    public static List<AddOn> getEntities(ArrayAttribute attArray) throws Exceptions.FactoryException {
        Attribute[] attributes = attArray.getAttribute();
        List<AddOn> addOns = new ArrayList<>();
        for (Attribute a : attributes) {
            try {
                AttributeMap map = (AttributeMap) a;
                addOns.add(getEntity(map));
            } catch (ClassCastException | NullPointerException e) {
                Exceptions.FactoryException ex = new Exceptions.FactoryException(e.getMessage());
                ex.setStackTrace(e.getStackTrace());
                throw ex;
            }
        }
        return addOns;
    }
}
