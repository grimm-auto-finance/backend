package routes;

import com.sun.net.httpserver.HttpExchange;

import entities.Car;

import entitypackagers.AttributizeCarUseCase;
import entitypackagers.JsonPackager;

import fetchers.DataBaseFetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/** The Route handling the `/search` route which allows users to search for a car with a string. */
public class Search extends Route {
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
    protected void post(HttpExchange t) throws IOException {
        OutputStream os = t.getResponseBody();
        InputStream is = t.getRequestBody();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }
        String searchString = sb.toString();

        try {
            List<Car> cars = DataBaseFetcher.search(searchString);
            JsonPackager jp = new JsonPackager();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (Car car : cars) {
                AttributizeCarUseCase uc = new AttributizeCarUseCase(car);
                try {
                    JsonObject json = jp.writePackage(uc.attributizeEntity()).getPackage();
                    arrayBuilder.add(json);
                } catch (constants.Exceptions.PackageException e) {
                    e.printStackTrace();
                }
            }
            String responseString = arrayBuilder.build().toString();
            t.sendResponseHeaders(200, responseString.length());
            os.write(responseString.getBytes());
        } catch (SQLException e) {
            throw new IOException();
        }
    }
}
