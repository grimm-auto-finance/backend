package entities;

import attributes.*;
import constants.EntityStringNames;
import constants.Exceptions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AddOnFactoryTest {

    @Test
    public void testGetEntityWorking() {
        AttributeMap addOnMap = new AttributeMap();
        addOnMap.addItem(EntityStringNames.ADD_ON_NAME, "Rust-proofing");
        addOnMap.addItem(EntityStringNames.ADD_ON_PRICE, 100.0);
        addOnMap.addItem(EntityStringNames.ADD_ON_DESCRIPTION, "no rust allowed!");
        AddOn testAddOn = new AddOn("Rust-proofing", 100.0, "no rust allowed!");
        try {
            assertEquals(testAddOn, AddOnFactory.getEntity(addOnMap));
        } catch (Exceptions.FactoryException e) {
            fail();
        }
    }

    @Test
    public void testGetEntityMissingKeys() {
        AttributeMap addOnMap = new AttributeMap();
        addOnMap.addItem("wrong key!", "rust-proofing");
        try {
            AddOnFactory.getEntity(addOnMap);
        } catch (Exceptions.FactoryException e) {
            return;
        }
        fail();
    }

    @Test
    public void testGetEntityWrongValueTypes() {
        AttributeMap addOnMap = new AttributeMap();
        addOnMap.addItem(EntityStringNames.ADD_ON_NAME, 15);
        addOnMap.addItem(EntityStringNames.ADD_ON_PRICE, "uh oh");
        addOnMap.addItem(EntityStringNames.ADD_ON_DESCRIPTION, 39.5);
        try {
            AddOnFactory.getEntity(addOnMap);
        } catch (Exceptions.FactoryException e) {
            return;
        }
        fail();
    }

    @Test
    public void testGetEntitiesWorking() {
        AttributeMap oneAddOnMap = new AttributeMap();
        oneAddOnMap.addItem(EntityStringNames.ADD_ON_NAME, "rust-proofing");
        oneAddOnMap.addItem(EntityStringNames.ADD_ON_PRICE, 15.0);
        oneAddOnMap.addItem(EntityStringNames.ADD_ON_DESCRIPTION, "no rust allowed!");
        AddOn oneAddOn = new AddOn("rust-proofing", 15.0, "no rust allowed!");
        AttributeMap twoAddOnMap = new AttributeMap();
        twoAddOnMap.addItem(EntityStringNames.ADD_ON_NAME, "marshmallows");
        twoAddOnMap.addItem(EntityStringNames.ADD_ON_PRICE, 25.25);
        twoAddOnMap.addItem(EntityStringNames.ADD_ON_DESCRIPTION, "fluffy goodness");
        AddOn twoAddOn = new AddOn("marshmallows", 25.25, "fluffy goodness");
        Attribute[] addOns = {oneAddOnMap, twoAddOnMap};
        ArrayAttribute addOnArray = (ArrayAttribute) AttributeFactory.createAttribute(addOns);
        List<AddOn> addOnList = new ArrayList<>();
        addOnList.add(oneAddOn);
        addOnList.add(twoAddOn);
        try {
            assertEquals(addOnList, AddOnFactory.getEntities(addOnArray));
        } catch (Exceptions.FactoryException e) {
            fail();
        }
    }

    @Test
    public void testGetEntitiesNotAttributeMap() {
        IntAttribute badInt = (IntAttribute) AttributeFactory.createAttribute(5);
        DoubleAttribute badDouble = (DoubleAttribute) AttributeFactory.createAttribute(25.3);
        Attribute[] badArray = {badInt, badDouble};
        ArrayAttribute badArrayAttribute = (ArrayAttribute) AttributeFactory.createAttribute(badArray);
        try {
            AddOnFactory.getEntities(badArrayAttribute);
        } catch (Exceptions.FactoryException e) {
            return;
        }
        fail();
    }
}
