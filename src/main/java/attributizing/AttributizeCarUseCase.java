// layer: usecases
package attributizing;

import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.AddOn;
import entities.Car;

import java.util.Map;

/** A Use Case to convert a Car into an AttributeMap */
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

    /** Write the given Car's data to an AttributeMap
     * The Car's add-ons are stored as a submap of the Car's map
     * @return an AttributeMap containing the Car's information
     * */
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

    /**
     * Converts a Map betweeen AddOn names and AddOn objects into an AttributeMap
     * The keys in this AttributeMap are AddOn names, and the values are AttributeMaps
     * representing AddOns
     * @param addOns the Map between Strings and AddOns to attributize
     * @return an AttributeMap representation of addOns
     */
    public static AttributeMap getAddOnMap(Map<String, AddOn> addOns) {
        AttributeMap map = new AttributeMap();
        for (String s : addOns.keySet()) {
            AttributizeAddOnUseCase addOnAttributizer = (AttributizeAddOnUseCase) AttributizerFactory.getAttributizer(addOns.get(s));
            map.addItem(addOns.get(s).getName(), addOnAttributizer.attributizeEntity());
        }
        return map;
    }
}
