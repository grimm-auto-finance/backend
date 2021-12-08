// layer: controllers
package routes;

import com.sun.net.httpserver.HttpExchange;

import constants.Exceptions;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/** The Route handling the `/addons` route which returns a car with its addons given the id */
public class Addons extends Route {

    private final DataBase dataBase;

    public Addons(DataBase dataBase, Logger logger, Parser parser, Packager packager) {
        super(logger, parser, packager);
        this.dataBase = dataBase;
    }

    @Override
    public String getContext() {
        return "/addons";
    }

    /**
     * The post method for the `/addons` route. Takes in an HttpExchange containing a Car id, and
     * responds with the list of Addons contained in the Car corresponding to that id.
     *
     * @param t the httpexchange that this method must handle
     */
    @Override
    protected void post(HttpExchange t) throws Exceptions.CodedException {
        int id = 0;
        try {
            id = getId(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Car car = getCar(id);
        String responseString = getResponseString(car);
        respond(t, 200, responseString.getBytes());
    }

    private String getResponseString(Car car) throws Exceptions.PackageException {
        PackageEntityUseCase packageEntity = new PackageEntityUseCase(packager);
        List<Entity> addOnsList = new ArrayList<>(car.getAddOnsList());
        Package addOnArrayPackage = packageEntity.writeEntitiesToArray(addOnsList);
        return addOnArrayPackage.toString();
    }

    private Car getCar(int id) throws Exceptions.CodedException {
        DataBaseFetcher fetcher = new DataBaseFetcher(dataBase);
        FetchCarDataUseCase carDataFetcher = new FetchCarDataUseCase(fetcher);
        return carDataFetcher.getCar(id);
    }

    private int getId(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        String result = new String(is.readAllBytes());
        return Integer.parseInt(result);
    }
}
