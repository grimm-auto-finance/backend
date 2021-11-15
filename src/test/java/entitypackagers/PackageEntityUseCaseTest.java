package entitypackagers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import attributes.AttributeMap;

import constants.Exceptions;

import entities.Entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PackageEntityUseCaseTest {

    static PackageEntityUseCase entityPackager;
    static JsonPackager jsonPackager;
    static Packager otherPackager;
    static Entity car;

    @BeforeAll
    static void setup() {
        entityPackager = new PackageEntityUseCase();
        jsonPackager = new JsonPackager();
        car = TestEntityCreator.getTestCar();
    }

    @Test
    public void testConstructor() {
        entityPackager = new PackageEntityUseCase(jsonPackager);
        assertEquals(jsonPackager, entityPackager.getPackager());
    }

    @Test
    public void testSetPackager() {
        entityPackager.setPackager(otherPackager);
        assertEquals(otherPackager, entityPackager.getPackager());
    }

    @Test
    public void testPackageEntityWorking() {
        entityPackager.setPackager(jsonPackager);
        Attributizer entityAttributizer = AttributizerFactory.getAttributizer(car);
        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(car.getStringName(), entityAttributizer.attributizeEntity());
        try {
            assertEquals(
                    jsonPackager.writePackage(entityMap).getPackage(),
                    entityPackager.writeEntity(car).getPackage());
        } catch (Exceptions.PackageException e) {
            fail();
        }
    }

    @Test
    public void testPackageNullPackager() {
        entityPackager = new PackageEntityUseCase();
        try {
            // Throws a NullPointerException because the value of the stored Packager is null
            entityPackager.writeEntity(car);
        } catch (NullPointerException e) {
            assertEquals("Can't use null Packager to package Entity", e.getMessage());
            return;
        } catch (Exceptions.PackageException e) {
            fail();
        }
        fail();
    }

    @Test
    public void testPackageNullEntity() {
        entityPackager = new PackageEntityUseCase(jsonPackager);
        try {
            entityPackager.writeEntity(null);
        } catch (NullPointerException e) {
            assertEquals("Can't extract Attributes from null Entity", e.getMessage());
            return;
        } catch (Exceptions.PackageException e) {
            fail();
        }
        fail();
    }
}
