// layer: interfaceadapters
package database;

import constants.Exceptions;

import java.sql.ResultSet;

public abstract class DataBase {

    /** Connect to the database and run migration operations (establish column names, types, etc) */
    public abstract void connectAndMigrate() throws Exceptions.DataBaseException;

    /** Insert placeholder data into the database */
    public abstract void insertPlaceholderData() throws Exceptions.DataBaseException;

    /**
     * Execute a database query
     *
     * @param query the query to be executed
     * @param queryParam sets the type of the ?s in the query
     * @return a ResultSet containing the results for the query from the DataBase
     * @throws Exceptions.DataBaseException if the query fails
     */
    public abstract ResultSet executeQuery(String query, Object queryParam)
            throws Exceptions.DataBaseException;
}
