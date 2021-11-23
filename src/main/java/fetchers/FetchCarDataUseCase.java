package fetchers;

import attributes.ArrayAttribute;
import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;
import entities.AddOn;
import entities.Car;
import entities.GenerateEntitiesUseCase;

import java.util.ArrayList;
import java.util.List;

public class FetchCarDataUseCase {

    private final Fetcher fetcher;

    public FetchCarDataUseCase(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

    public Car getCar(int id, boolean addOns) throws Exceptions.CodedException {
        String query = "SELECT * FROM cars WHERE id = ?;";
        try {
            fetcher.setFetchParam(id);
            ArrayAttribute resultsMapArrayAtt = (ArrayAttribute) fetcher.fetch(query);
            AttributeMap[] resultsMapArray = (AttributeMap[]) resultsMapArrayAtt.getAttribute();
            Car car = extractCar(resultsMapArray[0]);
            if (addOns) {
                FetchAddOnDataUseCase addOnFetcher = new FetchAddOnDataUseCase(fetcher);
                for (AddOn addOn : addOnFetcher.getAddOns(id)) {
                    car.addAddOn(addOn);
                }
            }
            return car;
        } catch (Exceptions.FactoryException | Exceptions.FetchException e) {
            throw new Exceptions.FetchException("could not fetch car from database" + e.getMessage(), e);
        }
    }

    public List<Car> search(String searchString) throws Exceptions.CodedException {
        String query =
                String.join(
                        "\n",
                        "SELECT * FROM cars",
                        "WHERE to_tsvector(make || ' ' || model || ' ' || year) @@"
                                + " websearch_to_tsquery(?)");
        try {
            fetcher.setFetchParam(searchString);
            ArrayAttribute resultsMapArrayAtt = (ArrayAttribute) fetcher.fetch(query);
            AttributeMap[] resultsMapArray = (AttributeMap[]) resultsMapArrayAtt.getAttribute();
            List<Car> cars = new ArrayList<>();
            for (AttributeMap carMap : resultsMapArray) {
                cars.add(extractCar(carMap));
            }
            return cars;
        } catch (Exceptions.FactoryException | Exceptions.FetchException e) {
            throw new Exceptions.FetchException(
                    "could not get search result from database" + e.getMessage(), e);
        }
    }

    private static Car extractCar(AttributeMap carMap) throws Exceptions.FactoryException {
        carMap.addItem(EntityStringNames.ADD_ON_STRING, new AttributeMap());
        carMap.addItem(EntityStringNames.CAR_KILOMETRES, 0.0);
        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(EntityStringNames.CAR_STRING, carMap);
        return GenerateEntitiesUseCase.generateCar(entityMap);
    }
}
