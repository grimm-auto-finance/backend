package fetchers;

import constants.Exceptions.CodedException;
import constants.Exceptions.FetchException;

import entities.AddOn;
import entities.Car;

import server.Env;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
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

    public static void insertPlaceholderData() throws CodedException {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("data/cars.csv"));
        } catch (FileNotFoundException e) {
            CodedException err = new FetchException(e.getMessage());
            err.setStackTrace(e.getStackTrace());
            throw err;
        }
        scanner.useDelimiter("\n");
        scanner.next();
        String line;
        while (scanner.hasNext()) {
            line = scanner.next();
            String[] fields = line.split(",");
            String statement = "INSERT INTO cars VALUES (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement pst = connection.prepareStatement(statement);
                pst.setInt(1, Integer.parseInt(fields[0]));
                pst.setDouble(2, Double.parseDouble(fields[4]));
                pst.setString(3, fields[1]);
                pst.setString(4, fields[2]);
                pst.setInt(5, Integer.parseInt(fields[3]));
                pst.setInt(6, 0);
                pst.execute();
            } catch (SQLException e) {
                CodedException err = new FetchException(e.getMessage());
                err.setStackTrace(e.getStackTrace());
                throw err;
            }
        }
    }

    public static Car getCar(int id, boolean addOns) throws CodedException {
        String query = "SELECT * FROM cars WHERE id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Car car = new Car(rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                if (addOns) {
                    for (AddOn addOn : getAddOns(id)) {
                        car.addAddOn(addOn);
                    }
                }
                return car;
            } else {
                return null;
            }
        } catch (SQLException e) {
            CodedException err = new FetchException(e.getMessage());
            err.setStackTrace(e.getStackTrace());
            throw err;
        }
    }

    public static List<Car> search(String searchString) throws CodedException {
        String query =
                String.join(
                        "\n",
                        "SELECT * FROM cars",
                        "WHERE to_tsvector(make || ' ' || model || ' ' || year) @@"
                                + " websearch_to_tsquery(?)");
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, searchString);
            ResultSet rs = pst.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (rs.next()) {
                cars.add(new Car(rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
            return cars;
        } catch (SQLException e) {
            CodedException err = new FetchException(e.getMessage());
            err.setStackTrace(e.getStackTrace());
            throw err;
        }
    }

    public static List<AddOn> getAddOns(int carId) throws CodedException {
        String query = "SELECT * FROM addons WHERE vid = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, carId);
            ResultSet rs = pst.executeQuery();
            List<AddOn> addOns = new ArrayList<>();
            while (rs.next()) {
                addOns.add(new AddOn(rs.getString(2), rs.getDouble(3), rs.getString(4)));
            }
            return addOns;
        } catch (SQLException e) {
            CodedException err = new FetchException(e.getMessage());
            err.setStackTrace(e.getStackTrace());
            throw err;
        }
    }
}
