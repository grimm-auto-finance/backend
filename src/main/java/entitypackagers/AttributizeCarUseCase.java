package entitypackagers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.AddOn;
import entities.Car;

import java.util.List;
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

        List<AddOn> addOns = car.getAddOns();
        Attribute[] addOnMaps = new Attribute[addOns.size()];
        for (int i = 0; i < addOns.size(); i++) {
            AttributizeAddOnUseCase addOnAttributizer = new AttributizeAddOnUseCase(addOns.get(i));
            addOnMaps[i] = addOnAttributizer.attributizeEntity();
        }
        Attribute addOnArray = AttributeFactory.createAttribute(addOnMaps);

        carMap.addItem(EntityStringNames.CAR_ADD_ONS, addOnArray);
        return carMap;
    }
}
