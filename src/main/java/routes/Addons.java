package routes;

import attributes.AttributeMap;

import com.sun.net.httpserver.HttpExchange;

import constants.Exceptions;

import entities.Car;

import entitypackagers.AttributizeCarUseCase;
import entitypackagers.ExtractCarIdUseCase;
import entitypackagers.JsonPackager;

import entityparsers.JsonParser;
import entityparsers.Parser;

import fetchers.DataBase;
import fetchers.DataBaseFetcher;
import fetchers.FetchCarDataUseCase;

import logging.Logger;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

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
     * responds with the Car corresponding to that id, along with all of its possible add-ons.
     *
     * @param t the httpexchange that this method must handle
     */
    @Override
    protected void post(HttpExchange t) throws Exceptions.CodedException {
        int id = getId(t);
        Car car = getCar(id);
        String responseString = getResponseString(car);
        respond(t, 200, responseString.getBytes());
    }

    private String getResponseString(Car car) throws Exceptions.PackageException {
        JsonPackager packager = new JsonPackager();
        AttributizeCarUseCase carAttributizer = new AttributizeCarUseCase(car);
        JsonObject json = packager.writePackage(carAttributizer.attributizeEntity()).getPackage();
        return json.toString();
    }

    private Car getCar(int id) throws Exceptions.CodedException {
        DataBaseFetcher fetcher = new DataBaseFetcher(dataBase);
        FetchCarDataUseCase carDataFetcher = new FetchCarDataUseCase(fetcher);
        return carDataFetcher.getCar(id);
    }

    private int getId(HttpExchange t) throws Exceptions.ParseException {
        InputStream is = t.getRequestBody();
        JsonReader jsonReader = Json.createReader(is);
        JsonObject inputObj = jsonReader.readObject();
        Parser jsonParser = new JsonParser(inputObj);
        AttributeMap entitiesMap = jsonParser.parse();
        return ExtractCarIdUseCase.extractId(entitiesMap);
    }
}
