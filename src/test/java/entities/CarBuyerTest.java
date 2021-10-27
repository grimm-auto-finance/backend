package entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChildCarBuyer extends CarBuyer {
    public ChildCarBuyer(int budget, int creditScore) {
        super(budget, creditScore);
    }
}

public class CarBuyerTest {
    @Test
    public void constructorAndGetters() {
        CarBuyer carBuyer = new CarBuyer(529, 540);
        assertEquals(529, carBuyer.getBudget());
        assertEquals(540, carBuyer.getCreditScore());
    }
}
