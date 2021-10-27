package entitypackagers;

import attributes.AttributeMap;
import entities.Entity;

public class PackageEntityUseCase {

    private final Entity entity;

    /**
     * Constructs a new PackageEntityUseCase to write the given Entity to a Package
     * @param entity the Entity to be serialized
     */
    public PackageEntityUseCase(Entity entity) {
        this.entity = entity;
    }

    /**
     * Writes entity to a Package using the given Packager
     * @param packager
     * @return
     * @throws Exception
     */
    public Package writeEntity(Packager packager) throws Exception {
        Attributizer entityAttributizer = AttributizerFactory.getAttributizer(entity);
        AttributeMap entityMap = entityAttributizer.attributizeEntity();
        return packager.writePackage(entityMap);
    }
}
