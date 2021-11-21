package entitypackagers;

import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.Car;

public class AttributizeCarIDUseCase {
    private final Object[] carAndId;

    public AttributizeCarIDUseCase(Object[] carAndID) {
        this.carAndId = carAndID;
    }

    public AttributeMap attributizeCarAndId() {
        AttributeMap carAndIdMap = new AttributeMap();
        carAndIdMap.addItem(EntityStringNames.ID_STRING, carAndId[0]);
        carAndIdMap.addItem(
                EntityStringNames.CAR_STRING,
                new AttributizeCarUseCase((Car) carAndId[1]).attributizeEntity());

        return carAndIdMap;
    }
}
