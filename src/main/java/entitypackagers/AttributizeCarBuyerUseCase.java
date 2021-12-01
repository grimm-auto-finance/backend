// layer: usecases
package entitypackagers;

import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.CarBuyer;

public class AttributizeCarBuyerUseCase implements Attributizer {

    private final CarBuyer buyer;

    /**
     * Constructs a new AttributizeCarBuyerUseCase that writes CarBuyer information to an
     * AttributeMap
     *
     * @param buyer the CarBuyer object to attributize
     */
    public AttributizeCarBuyerUseCase(CarBuyer buyer) {
        this.buyer = buyer;
    }

    /** Write the given CarBuyer's data to an AttributeMap */
    public AttributeMap attributizeEntity() {
        AttributeMap buyerMap = new AttributeMap();
        buyerMap.addItem(EntityStringNames.BUYER_BUDGET, buyer.getBudget());
        buyerMap.addItem(EntityStringNames.BUYER_CREDIT, buyer.getCreditScore());
        buyerMap.addItem(EntityStringNames.BUYER_DOWNPAYMENT, buyer.getDownPayment());
        return buyerMap;
    }
}
