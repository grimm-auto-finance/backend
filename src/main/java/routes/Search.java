package routes;

import com.sun.net.httpserver.HttpExchange;

import constants.Exceptions.CodedException;
import constants.Exceptions.ParseException;

import entities.Car;

import entitypackagers.AttributizeCarUseCase;
import entitypackagers.JsonPackager;

import fetchers.DataBase;
import fetchers.DataBaseFetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/** The Route handling the `/search` route which allows users to search for a car with a string. */
public class Search extends Route {
    private final DataBase dataBase;

    public Search(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public String getContext() {
        return "/search";
    }

    /**
     * The post method for the `/search` route.
     *
     * @param t the httpexchange that this method must handle
     */
    @Override
    protected void post(HttpExchange t) throws CodedException {
        InputStream is = t.getRequestBody();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String str;
        try {
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            CodedException err = new ParseException(e.getMessage());
            err.setStackTrace(e.getStackTrace());
            throw err;
        }
        String searchString = sb.toString();

        List<Car> cars;
        DataBaseFetcher fetcher = new DataBaseFetcher(dataBase);
        cars = fetcher.search(searchString);
        JsonPackager jp = new JsonPackager();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Car car : cars) {
            AttributizeCarUseCase uc = new AttributizeCarUseCase(car);
            JsonObject json = jp.writePackage(uc.attributizeEntity()).getPackage();
            arrayBuilder.add(json);
        }
        String responseString = arrayBuilder.build().toString();
        respond(t, 200, responseString.getBytes());
    }
}
