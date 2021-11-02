package entitypackagers;

import attributes.AttributeMap;
import constants.Exceptions;
import entities.AddOn;
import entities.Car;
import entities.CarBuyer;
import entities.Entity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PackageEntityUseCaseTest {

    static PackageEntityUseCase entityPackager;
    static JsonPackager jsonPackager;
    static Entity car;
    static Entity carBuyer;

    @BeforeAll
    static void setup() {
        entityPackager = new PackageEntityUseCase();
        jsonPackager = new JsonPackager();
        car = new Car(15.0, "Honda", "Civic", 2020);
        carBuyer = new CarBuyer(15000, 700);
    }

    @Test
    public void testConstructor() {
        entityPackager = new PackageEntityUseCase(car);
        assertEquals(car, entityPackager.getEntity());
    }

    @Test
    public void testSetEntity() {
        entityPackager.setEntity(carBuyer);
        assertEquals(carBuyer, entityPackager.getEntity());
    }

    @Test
    public void testPackageEntity() {
        entityPackager.setEntity(car);
        Attributizer entityAttributizer = AttributizerFactory.getAttributizer(car);
        AttributeMap entityMap = entityAttributizer.attributizeEntity();
        try {
            assertEquals(jsonPackager.writePackage(entityMap).getPackage(), entityPackager.writeEntity(jsonPackager).getPackage());
        } catch (Exceptions.PackageException e) {
            fail();
        }
    }

    @Test
    public void testPackageNullEntity() {
        entityPackager = new PackageEntityUseCase();
        try {
            // Throws a NullPointerException because the value of the stored Entity is null
            entityPackager.writeEntity(jsonPackager);
        } catch (NullPointerException e) {
            assertEquals("Can't extract Attributes from null Entity", e.getMessage());
            return;
        } catch (Exceptions.PackageException e) {
            fail();
        }
        fail();
    }
}
