package entitypackagers;

import attributes.AttributeMap;
import entities.Car;

public class PackageCarUseCase {

    private final Car car;

    /**
     * Constructs a new PackageCarUseCase to write data from the given Car
     * @param car
     */
    public PackageCarUseCase(Car car) {
        this.car = car;
    }

    /**
     * Writes the data from car to a Package using the given Packager
     * @param packager
     * @return
     * @throws Exception
     */
    public Package writeEntity(Packager packager) throws Exception {
        AttributizeCarUseCase carAttributizer = new AttributizeCarUseCase(car);
        AttributeMap carMap = carAttributizer.attributizeEntity();
        return packager.writePackage(carMap);
    }
}
