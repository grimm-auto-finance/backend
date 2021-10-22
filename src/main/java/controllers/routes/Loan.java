package routes;

import entities.Car;
import entities.CarBuyer;
import entities.LoanData;
import entitypackagers.PackageAllUseCase;
import entitypackagers.PackageLoanDataUseCase;
import entityparsers.ParseCarBuyerUseCase;
import entityparsers.ParseCarUseCase;
import fetchers.LoanDataFetcher;
import constants.Exceptions;

import java.io.*;
import java.net.HttpURLConnection;
import com.sun.net.httpserver.HttpExchange;
import javax.json.*;
import server.Env;
import java.io.BufferedReader;

public class Loan extends controllers.Route {
    @Override
    public String getContext() {
        return "/loan";
    }

    @Override
    protected void post(HttpExchange t) throws IOException {
        // TODO: Parse request body
        // TODO: Create objects from body

        OutputStream os = t.getResponseBody();

        InputStream is = t.getRequestBody();
        JsonReader jsonReader = Json.createReader(is);
        Car car;
        CarBuyer buyer;
        try {
            JsonObject inputObj = jsonReader.readObject();
            ParseCarUseCase carParser = new ParseCarUseCase(inputObj);
            ParseCarBuyerUseCase buyerParser = new ParseCarBuyerUseCase(inputObj);
            car = carParser.parse();
            // TODO: Remove this temporary setting to fetch from the database instead of being hard-coded
            car.setPrice(5000);
            buyer = buyerParser.parse();
            if (car.getMake() == null || car.getModel() == null) {
                String message = "Error in Payload JSON parsing";
                t.sendResponseHeaders(400, message.length());
                os.write(message.getBytes());
                os.close();
                return;
            }
        } catch (NullPointerException | ClassCastException e) {
            String message = "Error in Payload JSON parsing";
            t.sendResponseHeaders(400, message.length());
            os.write(message.getBytes());
            os.close();
            return;
        }


        try {
            LoanData loanData = LoanDataFetcher.fetch(buyer, car);

            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
            PackageAllUseCase allPackager = new PackageAllUseCase(jsonBuilder);
            allPackager.writeEntities(car, buyer, loanData);
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
        }
        os.close();
    }
}
