package entitypackagers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.CarBuyer;

public class AttributizeCarBuyerUseCase implements Attributizer {

  private final CarBuyer buyer;

  public AttributizeCarBuyerUseCase(CarBuyer buyer) {
    this.buyer = buyer;
  }

  public AttributeMap attributizeEntity() {
    AttributeMap buyerMap = new AttributeMap();
    buyerMap.addItem(EntityStringNames.BUYER_BUDGET, buyer.getBudget());
    buyerMap.addItem(EntityStringNames.BUYER_CREDIT, buyer.getCreditScore());
    return buyerMap;
  }
}
