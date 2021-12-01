package entitypackagers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;

import constants.Exceptions;

import entities.AddOn;
import entities.Car;
import entities.Entity;

import javax.json.JsonArray;

public class PackageEntityUseCase {

    private Packager packager;

    public PackageEntityUseCase() {}

    /**
     * Constructs a new PackageEntityUseCase to write the given Entity to a Package
     *
     * @param packager the Packager to use to turn Entities into Packages
     */
    public PackageEntityUseCase(Packager packager) {
        this.packager = packager;
    }

    /**
     * Sets the Packager that this PackageEntityUseCase uses to turn Entities into Packages
     *
     * @param packager the new Packager to be used
     */
    public void setPackager(Packager packager) {
        this.packager = packager;
    }

    /**
     * Returns the Packager that this PackageEntityUseCase has been set to use to package Should
     * only be used for testing
     *
     * @return The entity to be packaged
     */
    public Packager getPackager() {
        return this.packager;
    }

    /**
     * Writes the given Entity to a Package using packager
     *
     * @param entity the Entity to be packaged using Packager
     * @return a Package storing a representation of entity, made using Packager
     * @throws Exceptions.PackageException if the Attributization of entity fails
     */
    public Package writeEntity(Entity entity) throws Exceptions.PackageException {
        if (entity == null) {
            throw new NullPointerException("Can't extract Attributes from null Entity");
        } else if (packager == null) {
            throw new NullPointerException("Can't use null Packager to package Entity");
        }
        Attributizer entityAttributizer = AttributizerFactory.getAttributizer(entity);
        AttributeMap entityMap = entityAttributizer.attributizeEntity();
        return packager.writePackage(entityMap);
    }

    /**
     * @param car The car which addons we want to package
     * @return A JsonArray of all the cars addons
     * @throws Exceptions.PackageException Exception raised when the addons cannot be packaged
     *     correctly
     */
    public JsonArray getAddonJsonArray(Car car) throws Exceptions.PackageException {
        int addOnArraySize = car.getAddOns().keySet().size();
        Attribute[] addonArray = new Attribute[addOnArraySize];
        int count = 0;
        for (String addon : car.getAddOns().keySet()) {
            AddOn addOn = car.getAddOns().get(addon);
            AttributizeAddOnUseCase addOnAttributizer = new AttributizeAddOnUseCase(addOn);
            AttributeMap addOnAttribute = addOnAttributizer.attributizeEntity();
            addonArray[count] = addOnAttribute;
            count += 1;
        }
        ArrayAttribute addonArrayAttribute = new ArrayAttribute(addonArray);
        JsonPackager packager = new JsonPackager();

        return packager.getJsonArray(addonArrayAttribute);
    }
}
