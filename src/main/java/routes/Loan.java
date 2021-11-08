package routes;

import com.sun.net.httpserver.HttpExchange;

import constants.Exceptions.CodedException;

import entities.Car;
import entities.CarBuyer;
import entities.Entity;
import entities.LoanData;

import entitypackagers.JsonPackage;
import entitypackagers.JsonPackager;
import entitypackagers.PackageEntityUseCase;

import entityparsers.JsonParser;
import entityparsers.ParseCarBuyerUseCase;
import entityparsers.ParseCarUseCase;
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
        System.out.println("Loan 1 is reached!!!");
        InputStream is = t.getRequestBody();
        System.out.println("Loan 2 is reached!!!");
        JsonReader jsonReader = Json.createReader(is);
        System.out.println("Loan 3 is reached!!!");
        JsonObject inputObj = jsonReader.readObject();
        System.out.println("Loan 4 is reached!!!");
        Parser jsonParser = new JsonParser(inputObj);
        System.out.println("Loan 5 is reached!!!");
        ParseCarUseCase carParser = new ParseCarUseCase(jsonParser);
        System.out.println("Loan 5 is reached!!!");
        ParseCarBuyerUseCase buyerParser = new ParseCarBuyerUseCase(jsonParser);
        System.out.println("Loan 6 is reached!!!");
        Car car = carParser.parse();
        System.out.println("Loan 7 is reached!!!");
        CarBuyer buyer = buyerParser.parse();
        System.out.println("Loan 8 is reached!!!");
        // TODO: this check should be happening with ParseCarUseCase and ParseCarBuyerUseCase
        if (car.getMake() == null || car.getModel() == null) {
            System.out.println("Loan 9 is reached!!!");
            String message = "Error in Payload JSON parsing";
            respond(t, 400, message.getBytes());
            return;
        }
        System.out.println("Loan 10 is reached!!!");
        respond(t, 200, getResponse(buyer, car).getBytes());
        System.out.println("Loan 11 is reached!!!");
    }

    String getResponse(entities.CarBuyer buyer, entities.Car car) throws CodedException {
        System.out.println("Loan 12 is reached!!!");
        LoanData loanData = LoanDataFetcher.fetch(buyer, car);
        System.out.println("Loan 13 is reached!!!");
        // TODO: Abstract this more?
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        System.out.println("Loan 14 is reached!!!");
        List<Entity> entities = new ArrayList<>();
        System.out.println("Loan 15 is reached!!!");
        entities.add(car);
        System.out.println("Loan 16 is reached!!!");
        entities.add(buyer);
        System.out.println("Loan 17 is reached!!!");
        entities.add(loanData);
        System.out.println("Loan 18 is reached!!!");
        PackageEntityUseCase packageEntity = new PackageEntityUseCase();
        System.out.println("Loan 19 is reached!!!");
        for (Entity e : entities) {
            System.out.println("Loan 20 is reached!!!");
            packageEntity.setEntity(e);
            System.out.println("Loan 21 is reached!!!");
            JsonPackager jsonPackager = new JsonPackager();
            System.out.println("Loan 22 is reached!!!");
            JsonPackage entityPackage = (JsonPackage) packageEntity.writeEntity(jsonPackager);
            System.out.println("Loan 23 is reached!!!");
            jsonBuilder.add(e.getStringName(), entityPackage.getPackage());
            System.out.println("Loan 24 is reached!!!");
        }
        System.out.println("Loan 25 is reached!!!");
        return jsonBuilder.build().toString();
    }
}
