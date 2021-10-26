package entitypackagers;

import attributes.AttributeMap;
import constants.EntityStringNames;
import entities.CarBuyer;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.swing.text.html.parser.Entity;

public class PackageCarBuyerUseCase {
    private final CarBuyer carBuyer;

    /**
     * Constructs a new PackageCarBuyerUseCase that writes CarBuyer information to
     * given Packagers
     *
     * @param carBuyer the CarBuyer to serialize
     */
    public PackageCarBuyerUseCase(CarBuyer carBuyer) {
        this.carBuyer = carBuyer;
    }

    /**
     * Write the given CarBuyer's data to completeJsonBuilder
     *
     * @param packager the Packager to use to package carBuyer
     */
    public Package packageEntity(Packager packager) throws Exception {
        AttributizeCarBuyerUseCase buyerAttributizer = new AttributizeCarBuyerUseCase(carBuyer);
        AttributeMap buyerMap = buyerAttributizer.attributizeEntity();
        return packager.writePackage(buyerMap);
    }
}
