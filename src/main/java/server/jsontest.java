package server;

import entities.Car;
import entities.CarBuyer;
import entitypackagers.PackageCarBuyerUseCase;
import entitypackagers.PackageCarUseCase;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class jsontest {
    // TODO: turn this into a unit test
    public static void main(String[] args) {
        JsonObjectBuilder test = Json.createObjectBuilder();
        CarBuyer buyer = new CarBuyer(10000, 800);
        PackageCarBuyerUseCase packageBuyer = new PackageCarBuyerUseCase(test);
        packageBuyer.writeEntity(buyer);
        Car car = new Car(5, "Honda Civic", 2020);
        PackageCarUseCase packageCar = new PackageCarUseCase(test);
        packageCar.writeEntity(car);
        System.out.println(test.build());
    }
}
