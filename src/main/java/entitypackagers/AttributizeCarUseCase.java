package entitypackagers;

import attributes.AttributeMap;
import attributes.IntAttribute;
import attributes.StringAttribute;
import constants.EntityStringNames;
import entities.AddOn;
import entities.Car;

import java.util.Map;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.swing.text.html.parser.Entity;

public class AttributizeCarUseCase {
    private final Car car;

    /**
     * Constructs a new AttributizeCarUseCase that writes Car information to an AttributeMap
     *
     * @param car the Car object to attributize
     */
    public AttributizeCarUseCase(Car car) {
        this.car = car;
    }

    /**
     * Write the given Car's data to an AttributeMap
     */
    public AttributeMap attributizeEntity() {
        AttributeMap carMap = new AttributeMap();
        carMap.addItem(EntityStringNames.CAR_PRICE, car.getPrice());
        carMap.addItem(EntityStringNames.CAR_MAKE, car.getMake());
        carMap.addItem(EntityStringNames.CAR_MODEL, car.getModel());
        carMap.addItem(EntityStringNames.CAR_YEAR, car.getYear());

        AttributeMap addOnMap = new AttributeMap();
        Map<String, AddOn> addOns = car.getAddOns();
        for (String addOnName : addOns.keySet()) {
            AttributizeAddOnUseCase addOnAttributizer = new AttributizeAddOnUseCase(addOns.get(addOnName));
            carMap.addItem(addOnName + " " + EntityStringNames.ADD_ON_STRING, addOnAttributizer.attributizeEntity());
        }
        return carMap;
    }
}