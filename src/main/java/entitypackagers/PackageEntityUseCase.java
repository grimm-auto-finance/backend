package entitypackagers;

import attributes.AttributeMap;

import constants.Exceptions;

import entities.Entity;

public class PackageEntityUseCase {

    private Entity entity;

    public PackageEntityUseCase() {}

    /**
     * Constructs a new PackageEntityUseCase to write the given Entity to a Package
     *
     * @param entity the Entity to be serialized
     */
    public PackageEntityUseCase(Entity entity) {
        this.entity = entity;
    }

    /**
     * Sets the Entity that this PackageEntityUseCase packages to newEntity
     *
     * @param newEntity the new Entity to be packaged
     */
    public void setEntity(Entity newEntity) {
        this.entity = newEntity;
    }

    /**
     * Returns the Entity that this PackageEntityUseCase has been set to package Should only be used
     * for testing
     *
     * @return
     */
    public Entity getEntity() {
        return this.entity;
    }

    /**
     * Writes entity to a Package using the given Packager
     *
     * @param packager
     * @return
     * @throws Exception
     */
    public Package writeEntity(Packager packager) throws Exceptions.PackageException {
        if (entity == null) {
            throw new NullPointerException("Can't extract Attributes from null Entity");
        }
        Attributizer entityAttributizer = AttributizerFactory.getAttributizer(entity);
        AttributeMap entityMap = entityAttributizer.attributizeEntity();
        return packager.writePackage(entityMap);
    }
}
