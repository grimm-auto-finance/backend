package fetchers;

import entities.AddOn;
import entities.Car;

import server.Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public static Car getCar(int id, boolean addOns) throws SQLException {
        String query = "SELECT * FROM cars WHERE id = ?;";
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
    }

    public static List<Car> search(String searchString) throws SQLException {
        String query =
                String.join(
                        "\n",
                        "SELECT * FROM cars",
                        "WHERE to_tsvector(make || ' ' || model || ' ' || year) @@"
                                + " websearch_to_tsquery(?)");
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, searchString);
        ResultSet rs = pst.executeQuery();
        List<Car> cars = new ArrayList<>();
        while (rs.next()) {
            cars.add(new Car(rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
        }
        return cars;
    }

    public static List<AddOn> getAddOns(int carId) throws SQLException {
        String query = "SELECT * FROM addons WHERE vid = ?;";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, carId);
        ResultSet rs = pst.executeQuery();
        List<AddOn> addOns = new ArrayList<>();
        while (rs.next()) {
            addOns.add(new AddOn(rs.getString(2), rs.getDouble(3), rs.getString(4)));
        }
        return addOns;
    }
}