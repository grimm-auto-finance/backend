// Layer: interfaceadapters
package fetchers;

import constants.Exceptions;

import java.sql.ResultSet;

public abstract class DataBase {

    public abstract void connectAndMigrate() throws Exceptions.DataBaseException;

    public abstract void insertPlaceholderData() throws Exceptions.DataBaseException;

    abstract ResultSet executeQuery(String query, Object queryParam)
            throws Exceptions.DataBaseException;
}
