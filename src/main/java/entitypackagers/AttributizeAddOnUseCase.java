package entitypackagers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.AddOn;

public class AttributizeAddOnUseCase implements Attributizer {

    private final AddOn addOn;

    public AttributizeAddOnUseCase(AddOn addOn) {
        this.addOn = addOn;
    }

    public AttributeMap attributizeEntity() {
        AttributeMap addOnMap = new AttributeMap();
        addOnMap.addItem(EntityStringNames.ADD_ON_NAME, addOn.getName());
        addOnMap.addItem(EntityStringNames.ADD_ON_PRICE, addOn.getPrice());
        addOnMap.addItem(EntityStringNames.ADD_ON_DESCRIPTION, addOn.getDescription());
        return addOnMap;
    }
}
