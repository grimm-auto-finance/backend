// layer: controllers
package routes;

import com.sun.net.httpserver.HttpExchange;

import constants.Exceptions;
import constants.Exceptions.CodedException;
import constants.Exceptions.ParseException;

import entities.Car;
import entities.Entity;

import packaging.Package;
import packaging.PackageEntityUseCase;

import packaging.Packager;
import parsing.Parser;
import database.DataBase;
import fetching.DataBaseFetcher;
import fetching.FetchCarDataUseCase;

import logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/** The Route handling the `/search` route which allows users to search for a car with a string. */
public class Search extends Route {
    private final DataBase dataBase;

    /**
     * Constructs a new Search route with the given instance attributes
     * @param dataBase the database to search in
     * @param logger the logger to use for logging results/errors
     * @param parser the parser to use for input data
     * @param packager the packager to use for output data
     */
    public Search(DataBase dataBase, Logger logger, Parser parser, Packager packager) {
        super(logger, parser, packager);
        this.dataBase = dataBase;
    }

    /** Returns the HTTP URL context for this route: /search */
    @Override
    public String getContext() {
        return "/search";
    }

    /**
     * The post method for the `/search` route. Takes in an HttpExchange containing a search string,
     * and returns an array of Cars matching that string.
     *
     * @param t the httpexchange that this method must handle
     */
    @Override
    protected void post(HttpExchange t) throws CodedException {
        String searchString = getSearchString(t);
        List<Car> cars = getCarResultList(searchString);
        String responseString = getResponseString(cars);
        respond(t, 200, responseString.getBytes());
    }

    private String getResponseString(List<Car> cars) throws Exceptions.PackageException {
        PackageEntityUseCase carPackager = new PackageEntityUseCase(packager);
        List<Entity> entities = new ArrayList<>(cars);
        Package carsPackage = carPackager.writeEntitiesToArray(entities);
        return carsPackage.toString();
    }

    private List<Car> getCarResultList(String searchString) throws CodedException {
        List<Car> cars;
        DataBaseFetcher fetcher = new DataBaseFetcher(dataBase);
        FetchCarDataUseCase carDataFetcher = new FetchCarDataUseCase(fetcher);
        cars = carDataFetcher.search(searchString);
        return cars;
    }

    private String getSearchString(HttpExchange t) throws CodedException {
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
        return sb.toString();
    }
}
