package entitypackagers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.CarBuyer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttributizeCarBuyerUseCaseTest {

  static AttributeMap testMap;
  static CarBuyer buyer;
  static AttributizeCarBuyerUseCase buyerAttributizer;

  static void addBuyerToTestMap() {
    testMap.addItem(EntityStringNames.BUYER_BUDGET, buyer.getBudget());
    testMap.addItem(EntityStringNames.BUYER_CREDIT, buyer.getCreditScore());
  }

  @BeforeEach
  public void setup() {
    buyer = new CarBuyer(30000.5, 750);
    testMap = new AttributeMap();
    buyerAttributizer = new AttributizeCarBuyerUseCase(buyer);
    addBuyerToTestMap();
  }

  @Test
  public void testAttributizeCarBuyer() {
    assertEquals(
        testMap.getAttribute().toString(),
        buyerAttributizer.attributizeEntity().getAttribute().toString());
  }
}
