package routes;

import com.sun.net.httpserver.HttpExchange;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.Car;
import entities.CarBuyer;
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
import java.io.IOException;
import java.io.OutputStream;

import javax.json.*;

public class Loan extends controllers.Route {
    @Override
    public String getContext() {
        return "/loan";
    }

    @Override
    protected void post(HttpExchange t) throws IOException {
        OutputStream os = t.getResponseBody();

        InputStream is = t.getRequestBody();
        JsonReader jsonReader = Json.createReader(is);
        Car car;
        CarBuyer buyer;
        try {
            JsonObject inputObj = jsonReader.readObject();
            Parser jsonParser = new JsonParser(inputObj);
            ParseCarUseCase carParser = new ParseCarUseCase(jsonParser);
            ParseCarBuyerUseCase buyerParser = new ParseCarBuyerUseCase(jsonParser);
            car = carParser.parse();
            buyer = buyerParser.parse();
            if (car.getMake() == null || car.getModel() == null) {
                String message = "Error in Payload JSON parsing";
                t.sendResponseHeaders(400, message.length());
                os.write(message.getBytes());
                os.close();
                return;
            }
        } catch (Exceptions.ParseException | ClassCastException e) {
            e.printStackTrace();
            String message = "Error in Payload JSON parsing";
            t.sendResponseHeaders(400, message.length());
            os.write(message.getBytes());
            os.close();
            return;
        }

        try {
            LoanData loanData = LoanDataFetcher.fetch(buyer, car);
            // TODO: Abstract this more?
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

            PackageEntityUseCase packageEntities = new PackageEntityUseCase(car);
            JsonPackager jsonPackager = new JsonPackager();
            JsonPackage entityPackage = (JsonPackage) packageEntities.writeEntity(jsonPackager);
            jsonBuilder.add(EntityStringNames.CAR_STRING, entityPackage.getPackage());

            packageEntities.setEntity(buyer);
            entityPackage = (JsonPackage) packageEntities.writeEntity(jsonPackager);
            jsonBuilder.add(EntityStringNames.BUYER_STRING, entityPackage.getPackage());

            packageEntities.setEntity(loanData);
            entityPackage = (JsonPackage) packageEntities.writeEntity(jsonPackager);
            jsonBuilder.add(EntityStringNames.LOAN_STRING, entityPackage.getPackage());

            String responseString = jsonBuilder.build().toString();
            t.sendResponseHeaders(200, responseString.length());
            os.write(responseString.getBytes());
        } catch (Exceptions.CodedException e) {
            String message = e.getMessage();
            if (message != null) {
                t.sendResponseHeaders(e.getCode(), message.length());
                os.write(message.getBytes());
            } else {
                t.sendResponseHeaders(e.getCode(), 0);
            }
        } catch (Exception e) { //
            e.printStackTrace();
        }
        os.close();
    }
}
