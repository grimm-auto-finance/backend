package routes;

import com.sun.net.httpserver.HttpExchange;

import constants.EntityStringNames;
import constants.Exceptions;

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
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
      // TODO: this check should be happening with ParseCarUseCase and ParseCarBuyerUseCase
      if (car.getMake() == null || car.getModel() == null) {
        String message = "Error in Payload JSON parsing";
        t.sendResponseHeaders(400, message.length());
        os.write(message.getBytes());
        os.close();
        return;
      }
    } catch (Exceptions.CodedException e) {
      // TODO: printing this should be handled by the Logger
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
      List<Entity> entities = new ArrayList<>();
      entities.add(car);
      entities.add(buyer);
      entities.add(loanData);
      PackageEntityUseCase packageEntity = new PackageEntityUseCase();
      for (Entity e : entities) {
        packageEntity.setEntity(e);
        JsonPackager jsonPackager = new JsonPackager();
        JsonPackage entityPackage = (JsonPackage) packageEntity.writeEntity(jsonPackager);
        jsonBuilder.add(e.getStringName(), entityPackage.getPackage());
      }

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
