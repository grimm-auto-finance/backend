package entitypackagers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.CarBuyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttributizeCarBuyerUseCaseTest {

    static AttributeMap testMap;
    static CarBuyer buyer;
    static AttributizeCarBuyerUseCase buyerAttributizer;

    @BeforeEach
    public void setup() {
        buyer = new CarBuyer(30000.5, 750);
        testMap = new AttributeMap();
        buyerAttributizer = new AttributizeCarBuyerUseCase(buyer);
        addBuyerToTestMap();
    }

    static void addBuyerToTestMap() {
        testMap.addItem(EntityStringNames.BUYER_BUDGET, buyer.getBudget());
        testMap.addItem(EntityStringNames.BUYER_CREDIT, buyer.getCreditScore());
    }

    @Test
    public void testAttributizeCarBuyer() {
        assertEquals(
                testMap.getAttribute().toString(),
                buyerAttributizer.attributizeEntity().getAttribute().toString());
    }
}
