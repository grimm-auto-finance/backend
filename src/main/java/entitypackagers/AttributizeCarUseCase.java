package entitypackagers;

import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.AddOn;
import entities.Car;

import java.util.Map;

public class AttributizeCarUseCase implements Attributizer {
    private final Car car;

    /**
     * Constructs a new AttributizeCarUseCase that writes Car information to an AttributeMap
     *
     * @param car the Car object to attributize
     */
    public AttributizeCarUseCase(Car car) {
        this.car = car;
    }

    /** Write the given Car's data to an AttributeMap */
    public AttributeMap attributizeEntity() {
        AttributeMap carMap = new AttributeMap();
        carMap.addItem(EntityStringNames.CAR_PRICE, car.getPrice());
        carMap.addItem(EntityStringNames.CAR_MAKE, car.getMake());
        carMap.addItem(EntityStringNames.CAR_MODEL, car.getModel());
        carMap.addItem(EntityStringNames.CAR_YEAR, car.getYear());
        carMap.addItem(EntityStringNames.CAR_KILOMETRES, car.getKilometres());
        carMap.addItem(EntityStringNames.CAR_ID, car.getId());

        Map<String, AddOn> addOns = car.getAddOns();
        Attribute addOnMap = getAddOnMap(addOns);
        carMap.addItem(EntityStringNames.ADD_ON_STRING, addOnMap);

        return carMap;
    }

    public static AttributeMap getAddOnMap(Map<String, AddOn> addOns) {
        AttributeMap map = new AttributeMap();
        for (String s : addOns.keySet()) {
            AttributizeAddOnUseCase addOnAttributizer = new AttributizeAddOnUseCase(addOns.get(s));
            map.addItem(addOns.get(s).getName(), addOnAttributizer.attributizeEntity());
        }
        return map;
    }
}
