// layer: usecases
package fetching;

import attributes.ArrayAttribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.AddOn;
import entities.GenerateEntitiesUseCase;

import java.util.List;

public class FetchAddOnDataUseCase {

    private final Fetcher fetcher;

    /**
     * Constructs a new FetchAddOnDatauseCase to fetch AddOn information with the given Fetcher
     *
     * @param fetcher the Fetcher to use to retrieve AddOn information
     */
    public FetchAddOnDataUseCase(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

    /**
     * Gets the list of possible add-ons for the car with the given id
     *
     * @param carId the id of the car whose add-ons are to be returned
     * @return a List of AddOns, where each AddOn belongs to the car with the given id
     * @throws Exceptions.CodedException if the request fails
     */
    public List<AddOn> getAddOns(int carId) throws Exceptions.CodedException {
        String query = "SELECT * FROM addons WHERE vid = ?;";
        try {
            fetcher.setFetchParam(carId);
            ArrayAttribute resultsMapArrayAtt = (ArrayAttribute) fetcher.fetch(query);
            AttributeMap[] resultsMapArray = (AttributeMap[]) resultsMapArrayAtt.getAttribute();
            AttributeMap entityMap = new AttributeMap();
            entityMap.addItem(EntityStringNames.ADD_ON_STRING, resultsMapArray);
            return GenerateEntitiesUseCase.generateAddOnsFromArray(entityMap);
        } catch (Exceptions.FactoryException | Exceptions.FetchException e) {
            throw new Exceptions.FetchException("could not get add-ons from database", e);
        }
    }
}
