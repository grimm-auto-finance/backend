package routes;

import attributes.AttributeMap;
import com.sun.net.httpserver.HttpExchange;

import constants.Exceptions.CodedException;

import entities.*;

import entitypackagers.JsonPackage;
import entitypackagers.JsonPackager;
import entitypackagers.PackageEntityUseCase;

import entityparsers.JsonParser;
import entityparsers.Parser;

import fetchers.LoanDataFetcher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;

/** The Route handling the `/loan` route which allows users to fetch information about a loan. */
public class Loan extends Route {
    @Override
    public String getContext() {
        return "/loan";
    }

    /**
     * The post method for the `/loan` route.
     *
     * @param t the httpexchange that this method must handle
     */
    @Override
    protected void post(HttpExchange t) throws CodedException {
        InputStream is = t.getRequestBody();
        JsonReader jsonReader = Json.createReader(is);
        JsonObject inputObj = jsonReader.readObject();
        Parser jsonParser = new JsonParser(inputObj);
        AttributeMap entitiesMap = jsonParser.parse();
        Car car = GenerateEntitiesUseCase.generateCar(entitiesMap);
        CarBuyer buyer = GenerateEntitiesUseCase.generateCarBuyer(entitiesMap);
        respond(t, 200, getResponse(buyer, car).getBytes());
    }

    String getResponse(entities.CarBuyer buyer, entities.Car car) throws CodedException {
        LoanData loanData = LoanDataFetcher.fetch(buyer, car);
        List<Entity> entities = new ArrayList<>();
        entities.add(car);
        entities.add(buyer);
        entities.add(loanData);
        JsonPackager packager = new JsonPackager();
        PackageEntityUseCase packageEntity = new PackageEntityUseCase(packager);
        JsonPackage entitiesPackage = (JsonPackage) packageEntity.writeEntities(entities);
        return entitiesPackage.toString();
    }
}
