package routes;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;

import com.sun.net.httpserver.HttpExchange;

import constants.EntityStringNames;
import constants.Exceptions;
import constants.Exceptions.CodedException;
import constants.Exceptions.ParseException;

import entitypackagers.AttributizeCarIDUseCase;
import entitypackagers.JsonPackager;

import fetchers.DataBaseFetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
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
    protected void post(HttpExchange t) throws CodedException {
        InputStream is = t.getRequestBody();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        CreateSbfromSearchInput(reader, sb);
        String searchString = sb.toString();

        List<Object[]> carsID;
        carsID = DataBaseFetcher.search(searchString);
        JsonPackager jp = new JsonPackager();
        Attribute[] carAndIdMaps = new Attribute[carsID.size()];
        int count = 0;
        for (Object[] carsAndId : carsID) {
            AttributeMap uc = new AttributizeCarIDUseCase(carsAndId).attributizeCarAndId();
            carAndIdMaps[count] = uc;
            count += 1;
        }
        JsonArray json = AddJsonToJsonBuilder( jp, carAndIdMaps);

        String responseString = json.toString();
        respond(t, 200, responseString.getBytes());
    }

    private void CreateSbfromSearchInput(BufferedReader reader, StringBuilder sb)
            throws CodedException {
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
    }

    private JsonArray AddJsonToJsonBuilder(
            JsonPackager jp, Attribute[] carAndIdMaps)
            throws Exceptions.PackageException {
        ArrayAttribute carAndIdMapArray = new ArrayAttribute(carAndIdMaps);
        JsonArray json = jp.getJsonArray(carAndIdMapArray);
        return json;
    }
}
