package fetchers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;

import constants.EntityStringNames;

import entities.AddOn;
import entities.Car;
import entities.TestEntityCreator;

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
            addCarMaptoResultList(resultsList);
        } else if (request.equals(
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
                                + ") @@ websearch_to_tsquery(?)"))) {
            List<Car> carResults = new ArrayList<>();
            addItemsAndcarResultstoLists(resultsList, carResults);
        } else if (request.equals("SELECT * FROM addons WHERE vid = ?;")) {
            for (int i = 0; i < 3; i++) {
                AddOn addOn = TestEntityCreator.getTestAddOn();
                Attributizer addOnAttributizer = AttributizerFactory.getAttributizer(addOn);
                resultsList.add(addOnAttributizer.attributizeEntity());
            }
        }
        Attribute[] resultsArray = resultsList.toArray(new AttributeMap[0]);
        return (ArrayAttribute) AttributeFactory.createAttribute(resultsArray);
    }

    private void addCarMaptoResultList(List<AttributeMap> resultsList) {
        Car carResult = TestEntityCreator.getTestCar((int) queryParam);
        Attributizer carAttributizer = AttributizerFactory.getAttributizer(carResult);
        AttributeMap carMap = carAttributizer.attributizeEntity();
        resultsList.add(carMap);
    }

    private void addItemsAndcarResultstoLists(
            List<AttributeMap> resultsList, List<Car> carResults) {
        for (int i = 0; i < 5; i++) {
            carResults.add(TestEntityCreator.getTestCar());
        }
        for (Car c : carResults) {
            Attributizer carAttributizer = AttributizerFactory.getAttributizer(c);
            AttributeMap carMap = carAttributizer.attributizeEntity();
            carMap.addItem(
                    EntityStringNames.CAR_ID,
                    carMap.getItem(EntityStringNames.CAR_ID).getAttribute());
            carMap.addItem(
                    EntityStringNames.CAR_YEAR,
                    carMap.getItem(EntityStringNames.CAR_YEAR).getAttribute());
            resultsList.add(carMap);
        }
    }

    @Override
    public void setFetchParam(Object queryParam) {
        this.queryParam = queryParam;
    }
}
