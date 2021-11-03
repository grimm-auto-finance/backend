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

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    ParseCarUseCase carParser = new ParseCarUseCase(jsonParser);
    ParseCarBuyerUseCase buyerParser = new ParseCarBuyerUseCase(jsonParser);
    Car car = carParser.parse();
    CarBuyer buyer = buyerParser.parse();
    // TODO: this check should be happening with ParseCarUseCase and ParseCarBuyerUseCase
    if (car.getMake() == null || car.getModel() == null) {
      String message = "Error in Payload JSON parsing";
      respond(t, 400, message.getBytes());
      return;
    }
    respond(t, 200, getResponse(buyer, car).getBytes());
  }

  String getResponse(entities.CarBuyer buyer, entities.Car car) throws CodedException {
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

    return jsonBuilder.build().toString();
  }
}
