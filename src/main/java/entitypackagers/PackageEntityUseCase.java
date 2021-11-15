package entitypackagers;

import attributes.AttributeMap;

import constants.Exceptions;

import entities.Entity;

import java.util.List;

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
     * @return
     * @throws Exceptions.PackageException
     */
    public Package writeEntity(Entity entity) throws Exceptions.PackageException {
        if (entity == null) {
            throw new NullPointerException("Can't extract Attributes from null Entity");
        } else if (packager == null) {
            throw new NullPointerException("Can't use null Packager to package Entity");
        }
        Attributizer entityAttributizer = AttributizerFactory.getAttributizer(entity);
        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(entity.getStringName(), entityAttributizer.attributizeEntity());
        return packager.writePackage(entityMap);
    }

    public Package writeEntities(List<Entity> entities) throws Exceptions.PackageException {
        AttributeMap entitiesMap = new AttributeMap();
        if (packager == null) {
            throw new NullPointerException("Can't use null Packager to package Entity");
        }
        for (Entity e : entities) {
            if (e == null) {
                throw new NullPointerException("Can't extract Attributes from null Entity");
            }
            Attributizer entityAttributizer = AttributizerFactory.getAttributizer(e);
            AttributeMap entityMap = new AttributeMap();
            entityMap.addItem(e.getStringName(), entityAttributizer.attributizeEntity());
            entitiesMap = AttributeMap.combine(entitiesMap, entityMap);
        }
        return packager.writePackage(entitiesMap);
    }
}
