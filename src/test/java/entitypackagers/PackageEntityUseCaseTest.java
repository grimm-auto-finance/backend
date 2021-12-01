package entitypackagers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;

import constants.Exceptions;

import entities.Entity;
import entities.TestEntityCreator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void testWriteEntityWorking() {
        entityPackager.setPackager(jsonPackager);
        Attributizer entityAttributizer = AttributizerFactory.getAttributizer(car);
        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(car.getStringName(), entityAttributizer.attributizeEntity());
        try {
            assertEquals(
                    jsonPackager
                            .writePackage(entityMap)
                            .getPackage()
                            .asJsonObject()
                            .getJsonObject("car")
                            .toString(),
                    entityPackager.writeEntity(car).getPackage().toString());
        } catch (Exceptions.PackageException e) {
            fail();
        }
    }

    @Test
    public void testWriteEntitiesToArrayWorking() {
        entityPackager.setPackager(jsonPackager);
        List<Entity> entityList = new ArrayList<>();
        entityList.add(car);
        entityList.add(car);
        entityList.add(car);
        List<AttributeMap> entityMapList = new ArrayList<>();
        for (Entity e : entityList) {
            Attributizer entityAttributizer = AttributizerFactory.getAttributizer(car);
            entityMapList.add(entityAttributizer.attributizeEntity());
        }
        ArrayAttribute entityArray = (ArrayAttribute) AttributeFactory.createAttribute(entityMapList.toArray(new Attribute[0]));
        try {
            assertEquals(
                    jsonPackager
                            .writePackage(entityArray)
                            .getPackage()
                            .toString(),
                    entityPackager.writeEntitiesToArray(entityList).getPackage().toString());
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
