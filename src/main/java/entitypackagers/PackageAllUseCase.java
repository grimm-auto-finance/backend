package entitypackagers;

import entities.Car;
import entities.CarBuyer;
import entities.LoanData;

import javax.json.JsonObjectBuilder;

public class PackageAllUseCase {

    private final JsonObjectBuilder builder;

    /**
     * Constructs a new PackageAllUseCase that writes information about
     * Cars, CarBuyers, and LoanDatas to the given JsonObjectBuilder
     * @param builder the JsonObjectBuilder to serialize entities to
     */
    public PackageAllUseCase(JsonObjectBuilder builder) {
        this.builder = builder;
    }

    /**
     * Writes the given Car, CarBuyer, and LoanData to builder
     * @param car the Car to serialize
     * @param buyer the CarBuyer to serialize
     * @param loanData the LoanData to serialize
     */
    public void writeEntities(Car car, CarBuyer buyer, LoanData loanData) {
        PackageCarUseCase carPackager = new PackageCarUseCase(builder);
        carPackager.writeEntity(car);
        PackageCarBuyerUseCase buyerPackager = new PackageCarBuyerUseCase(builder);
        buyerPackager.writeEntity(buyer);
        PackageLoanDataUseCase loanPackager = new PackageLoanDataUseCase(builder);
        loanPackager.writeEntity(loanData);
    }
}
