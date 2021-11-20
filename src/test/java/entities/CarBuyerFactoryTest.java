package entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import org.junit.jupiter.api.Test;

public class CarBuyerFactoryTest {

    @Test
    public void testGetEntityWorking() {
        AttributeMap buyerMap = new AttributeMap();
        buyerMap.addItem(EntityStringNames.BUYER_BUDGET, 10000.0);
        // TODO: update this to be an int once we have parsing ints/doubles figured out
        buyerMap.addItem(EntityStringNames.BUYER_CREDIT, 750.0);
        buyerMap.addItem(EntityStringNames.BUYER_DOWNPAYMENT, 3000.0);
        CarBuyer testBuyer = new CarBuyer(10000.0, 750, 3000.0);
        try {
            System.out.println(CarBuyerFactory.getEntity(buyerMap).getBudget());
            System.out.println(CarBuyerFactory.getEntity(buyerMap).getCreditScore());
            System.out.println(CarBuyerFactory.getEntity(buyerMap).getDownPayment());
            System.out.println(testBuyer.getBudget());
            System.out.println(testBuyer.getCreditScore());
            System.out.println(testBuyer.getDownPayment());
            assertEquals(testBuyer, CarBuyerFactory.getEntity(buyerMap));
        } catch (Exceptions.FactoryException e) {
            fail();
        }
    }

    @Test
    public void testGetEntityMissingKeys() {
        AttributeMap buyerMap = new AttributeMap();
        buyerMap.addItem("wrong key!", "this won't work");
        try {
            CarBuyerFactory.getEntity(buyerMap);
        } catch (Exceptions.FactoryException e) {
            return;
        }
        fail();
    }

    @Test
    public void testGetEntityWrongValueTypes() {
        AttributeMap buyerMap = new AttributeMap();
        buyerMap.addItem(EntityStringNames.BUYER_BUDGET, "uh oh");
        buyerMap.addItem(EntityStringNames.BUYER_CREDIT, 36.25);
        buyerMap.addItem(EntityStringNames.BUYER_DOWNPAYMENT, "woah");
        try {
            CarBuyerFactory.getEntity(buyerMap);
        } catch (Exceptions.FactoryException e) {
            return;
        }
        fail();
    }
}