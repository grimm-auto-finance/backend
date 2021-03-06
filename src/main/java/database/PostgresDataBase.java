// layer: frameworksanddrivers
package database;

import constants.EntityStringNames;
import constants.Exceptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class PostgresDataBase extends DataBase {

    private Connection connection;
    private final String password;
    private final String URL;
    private final String username;

    /**
     * Constructs a new PostgresDataBase. This will connect to the given URL using the given
     * username and password
     *
     * @param URL the URL for the database connection
     * @param username the username to access the database
     * @param password the password to access the database
     */
    public PostgresDataBase(String URL, String username, String password) {
        this.URL = URL;
        this.username = username;
        this.password = password;
    }

    /**
     * Initializes the connection between this PostgresDataBase and the actual database hosted at
     * the given URL and sets up the columns of the database and declares their values
     *
     * @throws Exceptions.DataBaseException if the connection fails to initialize
     */
    public void connectAndMigrate() throws Exceptions.DataBaseException {
        String migrations =
                String.join(
                        "\n",
                        "CREATE TABLE IF NOT EXISTS cars (",
                        EntityStringNames.ID_STRING + " INT NOT NULL,",
                        EntityStringNames.CAR_PRICE + " NUMERIC(9, 2) NOT NULL,",
                        EntityStringNames.CAR_MAKE + " VARCHAR NOT NULL,",
                        EntityStringNames.CAR_MODEL + " VARCHAR NOT NULL,",
                        EntityStringNames.CAR_YEAR + " INT NOT NULL,",
                        EntityStringNames.CAR_KILOMETRES + " NUMERIC(9, 2) NOT NULL,",
                        EntityStringNames.CAR_IMAGE + " VARCHAR NOT NULL,",
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

    /**
     * Inserts placeholder data from data/cars.csv into the database.
     *
     * @throws Exceptions.DataBaseException if the insertion fails either due to cars.csv not
     *     existing or placeholder data already being present
     */
    public void insertPlaceholderData() throws Exceptions.DataBaseException {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("data/cars.csv"));
        } catch (FileNotFoundException e) {
            throw new Exceptions.DataBaseException("error reading car data file", e);
        }
        scanner.useDelimiter("\n");
        scanner.next();
        try {
            insertCarPlaceHolderData(scanner);
        } catch (SQLException e) {
            throw new Exceptions.DataBaseException("couldn't insert car placeholder data", e);
        }

        try {
            scanner = new Scanner(new File("data/addons.csv"));
        } catch (FileNotFoundException e) {
            throw new Exceptions.DataBaseException("error reading add-on data file", e);
        }
        scanner.useDelimiter("\n");
        scanner.next();
        try {
            insertAddOnPlaceholderData(scanner);
        } catch (SQLException e) {
            throw new Exceptions.DataBaseException("couldn't insert add-on placeholder data", e);
        }
    }

    private void insertAddOnPlaceholderData(Scanner scanner) throws SQLException {
        String line;
        while (scanner.hasNext()) {
            line = scanner.next();
            String[] fields = line.split(",");
            String statement = "INSERT INTO addons VALUES (?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";
            PreparedStatement pst = connection.prepareStatement(statement);
            pst.setInt(1, Integer.parseInt(fields[0]));
            pst.setString(2, fields[1]);
            pst.setDouble(3, Double.parseDouble(fields[2]));
            pst.setString(4, fields[3]);
            pst.setInt(5, Integer.parseInt(fields[4]));
            pst.execute();
        }
    }

    private void insertCarPlaceHolderData(Scanner scanner) throws SQLException {
        String line;
        while (scanner.hasNext()) {
            line = scanner.next();
            String[] fields = line.split(",");
            String statement =
                    "INSERT INTO cars VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";
            PreparedStatement pst = connection.prepareStatement(statement);
            pst.setInt(1, Integer.parseInt(fields[0]));
            pst.setDouble(2, Double.parseDouble(fields[5]));
            pst.setString(3, fields[2]);
            pst.setString(4, fields[3]);
            pst.setInt(5, Integer.parseInt(fields[4]));
            pst.setDouble(6, Double.parseDouble(fields[1]));
            pst.setString(7, fields[6]);
            pst.execute();
        }
    }

    /**
     * Executes a search query in the database that this PostgresDataBase is connected to
     *
     * @param query the search String
     * @param queryParam an Integer or String, setting whether the ?s in query represent numbers or
     *     strings
     * @return a ResultSet containing the results of the query
     * @throws Exceptions.DataBaseException if the query fails
     */
    public ResultSet executeQuery(String query, Object queryParam)
            throws Exceptions.DataBaseException {
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
            throw new Exceptions.DataBaseException(
                    "could not execute database query" + e.getMessage(), e);
        }
    }
}
