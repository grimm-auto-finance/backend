package fetchers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;
import constants.Exceptions.CodedException;
import constants.Exceptions.FetchException;

import entities.AddOn;
import entities.Car;
import entities.GenerateEntitiesUseCase;

import logging.LoggerFactory;

import server.Env;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataBaseFetcher implements Fetcher {

    private final DataBase database;
    private Object queryParam;

    public DataBaseFetcher(DataBase database) {
        this.database = database;
    }

    public void setFetchParam(Object queryParam) {
        this.queryParam = queryParam;
    }

    public Car getCar(int id, boolean addOns) throws CodedException {
        String query = "SELECT * FROM cars WHERE id = ?;";
        try {
            ResultSet rs = database.executeQuery(query, id);
            if (rs.next()) {
                Car car = extractCar(rs);
                if (addOns) {
                    for (AddOn addOn : getAddOns(id)) {
                        car.addAddOn(addOn);
                    }
                }
                return car;
            } else {
                return null;
            }
        } catch (SQLException | Exceptions.FactoryException e) {
            throw new FetchException("could not fetch car from database: " + e.getMessage(), e);
        }
    }

    public List<Car> search(String searchString) throws CodedException {
        String query =
                String.join(
                        "\n",
                        "SELECT * FROM cars",
                        "WHERE to_tsvector(make || ' ' || model || ' ' || year) @@"
                                + " websearch_to_tsquery(?)");
        try {
            ResultSet rs = database.executeQuery(query, searchString);
            List<Car> cars = new ArrayList<>();
            while (rs.next()) {
                cars.add(extractCar(rs));
            }
            return cars;
        } catch (SQLException | Exceptions.FactoryException e) {
            throw new FetchException(
                    "could not get search result from database: " + e.getMessage(), e);
        }
    }

    private static Car extractCar(ResultSet rs) throws SQLException, Exceptions.FactoryException {
        AttributeMap carMap = new AttributeMap();
        carMap.addItem(EntityStringNames.CAR_PRICE, rs.getDouble(2));
        carMap.addItem(EntityStringNames.CAR_MAKE, rs.getString(3));
        carMap.addItem(EntityStringNames.CAR_MODEL, rs.getString(4));
        carMap.addItem(EntityStringNames.CAR_YEAR, rs.getDouble(5));
        carMap.addItem(EntityStringNames.CAR_KILOMETRES, 0.0);
        carMap.addItem(EntityStringNames.ADD_ON_STRING, new AttributeMap());
        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(EntityStringNames.CAR_STRING, carMap);
        return GenerateEntitiesUseCase.generateCar(entityMap);
    }

    public ArrayAttribute fetch(String request) throws Exceptions.FetchException {
        ResultSet queryResult;
        try {
            queryResult = database.executeQuery(request, queryParam);
        } catch (Exceptions.DataBaseException e) {
            throw new FetchException("Failed to execute DataBase query with request" + request + " and parameter " + queryParam, e);
        }

        List<AttributeMap> resultsList = new ArrayList<>();
        try {
            while (queryResult.next()) {
                resultsList.add(parseResults(queryResult));
            }
        } catch (SQLException e) {
            throw new FetchException("Failed to parse database query results", e);
        }
        Attribute[] resultsArray = resultsList.toArray(new AttributeMap[0]);
        return (ArrayAttribute) AttributeFactory.createAttribute(resultsArray);
    }

    private AttributeMap parseResults(ResultSet rs) throws SQLException {
        AttributeMap resultMap = new AttributeMap();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            resultMap.addItem(metaData.getColumnName(i), rs.getObject(i));
        }
        return resultMap;
    }

    public List<AddOn> getAddOns(int carId) throws CodedException {
        String query = "SELECT * FROM addons WHERE vid = ?;";
        try {
            ResultSet rs = database.executeQuery(query, (Integer) carId);
            List<Attribute> addOnMapList = new ArrayList<>();
            while (rs.next()) {
                AttributeMap addOnMap = new AttributeMap();
                addOnMap.addItem(EntityStringNames.ADD_ON_NAME, rs.getString(2));
                addOnMap.addItem(EntityStringNames.ADD_ON_PRICE, rs.getDouble(3));
                addOnMap.addItem(EntityStringNames.ADD_ON_DESCRIPTION, rs.getString(4));
                addOnMapList.add(addOnMap);
            }
            ArrayAttribute addOnArray =
                    (ArrayAttribute) AttributeFactory.createAttribute(addOnMapList.toArray());
            AttributeMap entityMap = new AttributeMap();
            entityMap.addItem(EntityStringNames.ADD_ON_STRING, addOnArray);
            return GenerateEntitiesUseCase.generateAddOnsFromArray(entityMap);
        } catch (SQLException | Exceptions.FactoryException e) {
            throw new FetchException("could not get add-ons from database: " + e.getMessage(), e);
        }
    }
}
