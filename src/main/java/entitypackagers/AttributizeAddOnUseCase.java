package entitypackagers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.AddOn;

public class AttributizeAddOnUseCase implements Attributizer {

    private final AddOn addOn;

    /**
     * Constructs a new AttributizeAddOnUseCase that writes AddOn information to an AttributeMap
     *
     * @param addOn the AddOn object to attributize
     */
    public AttributizeAddOnUseCase(AddOn addOn) {
        this.addOn = addOn;
    }

    /** Write the given AddOns's data to an AttributeMap */
    public AttributeMap attributizeEntity() {
        AttributeMap addOnMap = new AttributeMap();
        addOnMap.addItem(EntityStringNames.ADD_ON_NAME, addOn.getName());
        addOnMap.addItem(EntityStringNames.ADD_ON_PRICE, addOn.getPrice());
        addOnMap.addItem(EntityStringNames.ADD_ON_DESCRIPTION, addOn.getDescription());
        return addOnMap;
    }
}
