package routes;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;

import com.sun.net.httpserver.HttpExchange;

import constants.Exceptions;

import entities.AddOn;
import entities.Car;

import entitypackagers.AttributizeAddOnUseCase;
import entitypackagers.JsonPackager;

import fetchers.DataBase;
import fetchers.DataBaseFetcher;
import fetchers.FetchCarDataUseCase;

import logging.Logger;

import java.io.IOException;
import java.io.InputStream;

import javax.json.*;

/** The Route handling the `/addons` route which returns a car with its addons given the id */
public class Addons extends Route {

    private final DataBase dataBase;

    public Addons(DataBase dataBase, Logger logger) {
        super(logger);
        this.dataBase = dataBase;
    }

    @Override
    public String getContext() {
        return "/addons";
    }

    /**
     * The post method for the `/addons` route. Takes in an HttpExchange containing a Car id, and
     * responds with the list of Addons contained in the Car corresponding to that id.
     *
     * @param t the httpexchange that this method must handle
     */
    @Override
    protected void post(HttpExchange t) throws Exceptions.CodedException {
        int id = 0;
        try {
            id = getId(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Car car = getCar(id);
        String responseString = getResponseString(car);
        respond(t, 200, responseString.getBytes());
    }

    private String getResponseString(Car car) throws Exceptions.PackageException {
        JsonPackager packager = new JsonPackager();
        ArrayAttribute addOnArrayAttribute = getArrayAttribute(car);
        JsonArray addonJsonArray = packager.getJsonArray(addOnArrayAttribute);
        return addonJsonArray.toString();
    }

    private Car getCar(int id) throws Exceptions.CodedException {
        DataBaseFetcher fetcher = new DataBaseFetcher(dataBase);
        FetchCarDataUseCase carDataFetcher = new FetchCarDataUseCase(fetcher);
        return carDataFetcher.getCar(id);
    }

    private int getId(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        String result = new String(is.readAllBytes());
        int id = Integer.parseInt(result);
        return id;
    }

    private ArrayAttribute getArrayAttribute(Car car) {
        int addOnArraySize = car.getAddOns().keySet().size();
        Attribute[] addonArray = new Attribute[addOnArraySize];
        int count = 0;
        for (String addon : car.getAddOns().keySet()) {
            AddOn addOn = car.getAddOns().get(addon);
            AttributizeAddOnUseCase addOnAttributizer = new AttributizeAddOnUseCase(addOn);
            AttributeMap addOnAttribute = addOnAttributizer.attributizeEntity();
            addonArray[count] = addOnAttribute;
            count += 1;
        }
        return new ArrayAttribute(addonArray);
    }
}
