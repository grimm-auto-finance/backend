// layer: usecases
package attributizing;

import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.CarBuyer;

/** A Use Case to convert a CarBuyer into an AttributeMap */
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

    /**
     * Write the given CarBuyer's data to an AttributeMap
     *
     * @return an AttributeMap containing the CarBuyer's information
     */
    public AttributeMap attributizeEntity() {
        AttributeMap buyerMap = new AttributeMap();
        buyerMap.addItem(EntityStringNames.BUYER_BUDGET, buyer.getBudget());
        buyerMap.addItem(EntityStringNames.BUYER_CREDIT, buyer.getCreditScore());
        buyerMap.addItem(EntityStringNames.BUYER_DOWNPAYMENT, buyer.getDownPayment());
        return buyerMap;
    }
}
