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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataBaseFetcher {
    private static Connection connection;

    public static void connectAndMigrate() throws SQLException {
        String migrations =
                String.join(
                        "\n",
                        "CREATE TABLE IF NOT EXISTS cars (",
                        "id INT NOT NULL,",
                        "price NUMERIC(9, 2) NOT NULL,",
                        "make VARCHAR NOT NULL,",
                        "model VARCHAR NOT NULL,",
                        "year INT NOT NULL,",
                        "kms INT,",
                        "PRIMARY KEY (id)",
                        ");",
                        "CREATE TABLE IF NOT EXISTS addons (",
                        "id INT NOT NULL,",
                        "name VARCHAR NOT NULL,",
                        "price NUMERIC(9, 2) NOT NULL,",
                        "descr VARCHAR NOT NULL,",
                        "vid INT NOT NULL,",
                        "PRIMARY KEY (id)",
                        ");");
        Connection connection =
                DriverManager.getConnection(
                        "jdbc:postgresql://db:5432/postgres", "postgres", Env.POSTGRES_PASSWORD);
        Statement st = connection.createStatement();
        st.execute(migrations);
        DataBaseFetcher.connection = connection;
    }

    public static void insertPlaceholderData() throws FileNotFoundException {
        Scanner scanner;
        scanner = new Scanner(new File("data/cars.csv"));
        scanner.useDelimiter("\n");
        scanner.next();
        String line;
        try {
            while (scanner.hasNext()) {
                line = scanner.next();
                String[] fields = line.split(",");
                String statement = "INSERT INTO cars VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = connection.prepareStatement(statement);
                pst.setInt(1, Integer.parseInt(fields[0]));
                pst.setDouble(2, Double.parseDouble(fields[5]));
                pst.setString(3, fields[2]);
                pst.setString(4, fields[3]);
                pst.setInt(5, Integer.parseInt(fields[4]));
                pst.setInt(6, Integer.parseInt(fields[1]));
                pst.execute();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger().info("placeholder data may already exist");
        }
    }

    public static Car getCar(int id) throws CodedException {
        String query = "SELECT * FROM cars WHERE id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Car car = extractCar(rs);
                for (AddOn addOn : getAddOns(id)) {
                    car.addAddOn(addOn);
                }
                return car;
            } else {
                return null;
            }
        } catch (SQLException | Exceptions.FactoryException e) {
            throw new FetchException("could not fetch car from database: " + e.getMessage(), e);
        }
    }

    public static List<Car> search(String searchString) throws CodedException {
        String query =
                String.join(
                        "\n",
                        "SELECT * FROM cars",
                        "WHERE to_tsvector(id || ' ' || price || ' ' || make || ' ' || model || ' '"
                                + " || year || ' ' || kms) @@ websearch_to_tsquery(?)");
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, searchString);
            ResultSet rs = pst.executeQuery();
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
        carMap.addItem(EntityStringNames.CAR_ID, (int) rs.getDouble(1));
        carMap.addItem(EntityStringNames.CAR_PRICE, rs.getDouble(2));
        carMap.addItem(EntityStringNames.CAR_MAKE, rs.getString(3));
        carMap.addItem(EntityStringNames.CAR_MODEL, rs.getString(4));
        carMap.addItem(EntityStringNames.CAR_YEAR, rs.getDouble(5));
        carMap.addItem(EntityStringNames.CAR_KILOMETRES, rs.getDouble(6));
        carMap.addItem(EntityStringNames.ADD_ON_STRING, new AttributeMap());
        // AttributeFactory.createAttribute(new Attribute[0]));
        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(EntityStringNames.CAR_STRING, carMap);
        return GenerateEntitiesUseCase.generateCar(entityMap);
    }

    public static List<AddOn> getAddOns(int carId) throws CodedException {
        String query = "SELECT * FROM addons WHERE vid = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, carId);
            ResultSet rs = pst.executeQuery();
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
