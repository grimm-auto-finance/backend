package fetchers;

import attributes.ArrayAttribute;
import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;
import entities.AddOn;
import entities.GenerateEntitiesUseCase;

import java.util.List;

public class FetchAddOnDataUseCase {

    private final Fetcher fetcher;

    public FetchAddOnDataUseCase(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

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
            throw new Exceptions.FetchException("could not get add-ons from database" + e.getMessage(), e);
        }
    }
}
