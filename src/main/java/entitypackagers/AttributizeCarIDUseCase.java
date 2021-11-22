package entitypackagers;

import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.Car;

public class AttributizeCarIDUseCase {
    private final int id;
    private final Car car;

    public AttributizeCarIDUseCase(int id, Car car) {
        this.id = id;
        this.car = car;
    }

    public AttributeMap attributizeCarAndId() {
        AttributeMap carAndIdMap = new AttributeMap();
        carAndIdMap.addItem(EntityStringNames.ID_STRING, this.id);
        carAndIdMap.addItem(
                EntityStringNames.ID_CAR_STRING,
                new AttributizeCarUseCase(this.car).attributizeEntity());

        return carAndIdMap;
    }
}
