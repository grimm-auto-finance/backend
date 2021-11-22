package routes;

import attributes.AttributeMap;

import com.sun.net.httpserver.HttpExchange;

import constants.Exceptions;

import entities.Car;

import entitypackagers.AttributizeCarUseCase;
import entitypackagers.GenerateIdUseCase;
import entitypackagers.JsonPackager;

import entityparsers.JsonParser;
import entityparsers.Parser;

import fetchers.DataBaseFetcher;

import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;

/** The Route handling the `/addons` route which returns a car with its addons given the id */
public class Addons extends Route {
    @Override
    public String getContext() {
        return "/addons";
    }

    /**
     * The post method for the `/addons` route.
     *
     * @param t the httpexchange that this method must handle
     */
    @Override
    protected void post(HttpExchange t) throws Exceptions.CodedException {
        InputStream is = t.getRequestBody();
        JsonReader jsonReader = Json.createReader(is);
        JsonObject inputObj = jsonReader.readObject();
        Parser jsonParser = new JsonParser(inputObj);
        AttributeMap entitiesMap = jsonParser.parse();
        int id = GenerateIdUseCase.generateId(entitiesMap);
        Car car = DataBaseFetcher.getCar(id);
        JsonPackager jp = new JsonPackager();
        AttributizeCarUseCase uc = new AttributizeCarUseCase(car);
        JsonObject json = jp.writePackage(uc.attributizeEntity()).getPackage();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        arrayBuilder.add(json);
        String responseString = arrayBuilder.build().toString();
        respond(t, 200, responseString.getBytes());
    }
}
