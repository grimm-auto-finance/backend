package fetchers;

import constants.EntityStringNames;
import constants.Exceptions;
import logging.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class PostgresDataBase extends DataBase {

    private Connection connection;
    private final String password;
    private final String URL;
    private final String username;

    public PostgresDataBase(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }

    public void connectAndMigrate() throws Exceptions.DataBaseException {
        String migrations =
                //TODO: make column names use EntityStringNames values
                String.join(
                        "\n",
                        "CREATE TABLE IF NOT EXISTS cars (",
                        "id INT NOT NULL,",
                        EntityStringNames.CAR_PRICE + " NUMERIC(9, 2) NOT NULL,",
                        EntityStringNames.CAR_MAKE + " VARCHAR NOT NULL,",
                        EntityStringNames.CAR_MODEL + " VARCHAR NOT NULL,",
                        EntityStringNames.CAR_YEAR + " INT NOT NULL,",
                        EntityStringNames.CAR_KILOMETRES + " INT,",
                        "PRIMARY KEY (id)",
                        ");",
                        "CREATE TABLE IF NOT EXISTS addons (",
                        "id INT NOT NULL,",
                        EntityStringNames.ADD_ON_NAME + " VARCHAR NOT NULL,",
                        EntityStringNames.ADD_ON_PRICE + " NUMERIC(9, 2) NOT NULL,",
                        EntityStringNames.ADD_ON_DESCRIPTION + " VARCHAR NOT NULL,",
                        "vid INT NOT NULL,",
                        "PRIMARY KEY (id)",
                        ");");
        try {
            connection = DriverManager.getConnection(URL, username, password);
            Statement st = connection.createStatement();
            st.execute(migrations);
        } catch (SQLException e) {
            throw new Exceptions.DataBaseException("error initializing postgres database", e);
        }
    }

    public void insertPlaceholderData() throws Exceptions.DataBaseException {
        Scanner scanner;
        try {
        scanner = new Scanner(new File("data/cars.csv"));
        } catch (FileNotFoundException e) {
            throw new Exceptions.DataBaseException("error reading car data file", e);
        }
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
            throw new Exceptions.DataBaseException("placeholder data may already exist", e);
        }
    }

    public ResultSet executeQuery(String query, Object queryParam) throws Exceptions.DataBaseException {
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            if (queryParam instanceof Integer) {
                pst.setInt(1, (int) queryParam);
            } else if (queryParam instanceof String) {
                pst.setString(1, (String) queryParam);
            } else {
                throw new Exceptions.DataBaseException("invalid database query parameter");
            }
            return pst.executeQuery();
        } catch (SQLException e) {
            throw new Exceptions.DataBaseException("could not execute database query" + e.getMessage(), e);
        }
    }
}
