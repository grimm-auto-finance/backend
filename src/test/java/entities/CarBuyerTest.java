package entities;

import constants.EntityStringNames;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarBuyerTest {

    static CarBuyer buyer;

    @BeforeAll
    static void setup() {
        buyer = new CarBuyer(50000, 750);
    }

    @Test
    public void testGetBudget() {
        assertEquals(50000, buyer.getBudget());
    }

    @Test
    public void testGetCreditScore() {
        assertEquals(750, buyer.getCreditScore());
    }

    @Test
    public void testGetStringName() {
        assertEquals(EntityStringNames.BUYER_STRING, buyer.getStringName());
    }
}
