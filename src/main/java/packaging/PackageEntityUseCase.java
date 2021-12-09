package packaging;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;

import attributizing.Attributizer;
import attributizing.AttributizerFactory;
import constants.Exceptions;

import entities.Entity;

import java.util.ArrayList;
import java.util.List;

/** A use case for converting Entities into Packages */
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
     * Writes the given List of Entities to a Package using packager The Entities are packaged as an
     * Array.
     *
     * @param entities the List of Entities to be packaged
     * @return a Package containing an array representation of entities
     * @throws Exceptions.PackageException if the Attributization of the entities fails
     */
    public Package writeEntitiesToArray(List<Entity> entities) throws Exceptions.PackageException {
        if (packager == null) {
            throw new NullPointerException("Can't use null Packager to package Entity");
        }
        List<AttributeMap> entitiesMapList = new ArrayList<>();
        for (Entity e : entities) {
            if (e == null) {
                throw new NullPointerException("Can't extract Attributes from null Entity");
            }
            Attributizer entityAttributizer = AttributizerFactory.getAttributizer(e);
            AttributeMap entityMap = entityAttributizer.attributizeEntity();
            entitiesMapList.add(entityMap);
        }
        ArrayAttribute entitiesMapArray =
                (ArrayAttribute)
                        AttributeFactory.createAttribute(entitiesMapList.toArray(new Attribute[0]));
        return packager.writePackage(entitiesMapArray);
    }
}
