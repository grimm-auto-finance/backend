// layer: usecases
package fetching;

import attributes.ArrayAttribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.AddOn;
import entities.Car;
import entities.GenerateEntitiesUseCase;

import java.util.ArrayList;
import java.util.List;

/** A use case for fetching Cars from a database */
public class FetchCarDataUseCase {

    private final Fetcher fetcher;

    /**
     * Constructs a new FetchCarDataUseCase to fetch Cars with the given Fetcher
     *
     * @param fetcher the Fetcher to use to retrieve Car information
     */
    public FetchCarDataUseCase(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

    /**
     * Get the Car with the given id. If addOns, include the Car's list of possible Add-ons in the
     * result
     *
     * @param id the id of the Car to be fetched
     * @return a Car with the given id
     * @throws Exceptions.CodedException if the fetch fails
     */
    public Car getCar(int id) throws Exceptions.CodedException {
        String query = "SELECT * FROM cars WHERE id = ?;";
        try {
            fetcher.setFetchParam(id);
            ArrayAttribute resultsMapArrayAtt = (ArrayAttribute) fetcher.fetch(query);
            AttributeMap[] resultsMapArray = (AttributeMap[]) resultsMapArrayAtt.getAttribute();
            Car car = extractCar(resultsMapArray[0]);
            FetchAddOnDataUseCase addOnFetcher = new FetchAddOnDataUseCase(fetcher);
            for (AddOn addOn : addOnFetcher.getAddOns(id)) {
                car.addAddOn(addOn);
            }
            return car;
        } catch (Exceptions.FactoryException | Exceptions.FetchException e) {
            throw new Exceptions.FetchException("could not fetch car from database", e);
        }
    }

    /**
     * Search for cars whose make, model, and/or year match the search String
     *
     * @param searchString the String used for the search
     * @return a List of Cars matching the search string
     * @throws Exceptions.CodedException if the search request fails
     */
    public List<Car> search(String searchString) throws Exceptions.CodedException {
        String query =
                String.join(
                        "\n",
                        "SELECT * FROM cars",
                        "WHERE to_tsvector("
                                + EntityStringNames.CAR_PRICE
                                + " || ' ' || "
                                + EntityStringNames.CAR_MAKE
                                + " || ' ' || "
                                + EntityStringNames.CAR_MODEL
                                + " || ' '"
                                + " || "
                                + EntityStringNames.CAR_YEAR
                                + ") @@ websearch_to_tsquery(?)");
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
            throw new Exceptions.FetchException("could not get search result from database", e);
        }
    }

    private static Car extractCar(AttributeMap carMap) throws Exceptions.FactoryException {
        carMap.addItem(EntityStringNames.ADD_ON_STRING, new AttributeMap());
        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(EntityStringNames.CAR_STRING, carMap);
        return GenerateEntitiesUseCase.generateCar(entityMap);
    }
}
