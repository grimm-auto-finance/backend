package fetchers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;
import entities.Car;
import entities.TestEntityCreator;
import entitypackagers.AttributizeCarUseCase;
import entitypackagers.Attributizer;
import entitypackagers.AttributizerFactory;

import java.util.ArrayList;
import java.util.List;

public class FakeDataBaseFetcher implements Fetcher {

    Object queryParam;

    @Override
    public ArrayAttribute fetch(String request) {
        List<AttributeMap> resultsList = new ArrayList<>();
        if (request.equals("SELECT * FROM cars WHERE id = ?;")) {
            Car carResult = TestEntityCreator.getTestCar((int) queryParam);
            Attributizer carAttributizer = AttributizerFactory.getAttributizer(carResult);
            AttributeMap carMap = carAttributizer.attributizeEntity();
            carMap.addItem(EntityStringNames.CAR_ID, queryParam);
            carMap.addItem(EntityStringNames.CAR_YEAR, carMap.getItem(EntityStringNames.CAR_YEAR).getAttribute());
            resultsList.add(carMap);
        } else if (request.equals(String.join(
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
                        + " || ' ' || "
                        + EntityStringNames.CAR_KILOMETRES
                        + ") @@ websearch_to_tsquery(?)"))) {
            List<Car> carResults = new ArrayList<>();
            carResults.add(TestEntityCreator.getTestCar(3));
            carResults.add(TestEntityCreator.getTestCar(5));
            for (Car c : carResults) {
                Attributizer carAttributizer = AttributizerFactory.getAttributizer(c);
                AttributeMap carMap = carAttributizer.attributizeEntity();
                carMap.addItem(EntityStringNames.CAR_ID, carMap.getItem(EntityStringNames.CAR_ID).getAttribute());
                carMap.addItem(EntityStringNames.CAR_YEAR, carMap.getItem(EntityStringNames.CAR_YEAR).getAttribute());
                resultsList.add(carMap);
            }
        }
        Attribute[] resultsArray = resultsList.toArray(new AttributeMap[0]);
        return (ArrayAttribute) AttributeFactory.createAttribute(resultsArray);
    }

    @Override
    public void setFetchParam(Object queryParam) {
        this.queryParam = queryParam;
    }
}
