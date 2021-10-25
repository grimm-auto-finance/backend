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
        carMap.addItem(EntityStringNames.CAR_PRICE, new IntAttribute(car.getPrice()));
        carMap.addItem(EntityStringNames.CAR_MAKE, new StringAttribute(car.getMake()));
        carMap.addItem(EntityStringNames.CAR_MODEL, new StringAttribute(car.getModel()));
        carMap.addItem(EntityStringNames.CAR_YEAR, new IntAttribute(car.getYear()));

        // create a new JsonObjectBuilder to handle serializing this car's add-ons
        // this ensures that the add-ons are listed together in a sub-entry of the Json object
        JsonObjectBuilder addOnJsonBuilder = Json.createObjectBuilder();
        PackageAddOnUseCase addOnPackager = new PackageAddOnUseCase(addOnJsonBuilder);
        Map<String, AddOn> addOns = car.getAddOns();
        for (String addOnName : addOns.keySet()) {
            addOnPackager.writeEntity(addOns.get(addOnName));
        }

        AttributeMap addOnMap = new AttributeMap();
        Map<String, AddOn> addOns = car.getAddOns();
        for (String addOnName : addOns.keySet()) {
            PackageAddOnUseCase addOnAttributizer = new PackageAddOnUseCase(addOns.get(addOnName));
            carMap.addItem(addOnName + " " + EntityStringNames.ADD_ON_STRING, addOnAttributizer.attributizeEntity());
        }
        return carMap;
    }
}
