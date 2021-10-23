package entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChildCarBuyer extends CarBuyer {
    public ChildCarBuyer(double budget, int creditScore) {
        super(budget, creditScore);
    }
}

public class CarBuyerTest {
    @Test
    public void constructorAndGetters() {
        CarBuyer carBuyer = new CarBuyer(529.56, 540);
        assertEquals(529.56, carBuyer.getBudget());
        assertEquals(540, carBuyer.getCreditScore());
    }
}