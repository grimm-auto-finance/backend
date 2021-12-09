// layer: gateways
package fetching;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;

import constants.Exceptions;
import constants.Exceptions.FetchException;

import database.DataBase;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** A Fetcher gateway for retrieving information from a DataBase */
public class DataBaseFetcher implements Fetcher {

    private final DataBase database;
    private Object queryParam;

    /**
     * Constructs a new DataBaseFetcher to fetch data from the given DataBase
     *
     * @param database the DataBase to fetch from
     */
    public DataBaseFetcher(DataBase database) {
        this.database = database;
    }

    /**
     * Sets the Fetch parameter for fetch requests to database
     *
     * @param queryParam the parameter for the fetch requestsi
     */
    public void setFetchParam(Object queryParam) {
        this.queryParam = queryParam;
    }

    /**
     * Make a request to database, and return an ArrayAttribute containing the results
     *
     * @param request the request to be made to database
     * @return an ArrayAttribute where each entry represents a row of results from the database
     * @throws Exceptions.FetchException if the reqeust fails
     */
    public ArrayAttribute fetch(String request) throws Exceptions.FetchException {
        ResultSet queryResult;
        try {
            queryResult = database.executeQuery(request, queryParam);
        } catch (Exceptions.DataBaseException e) {
            throw new FetchException(
                    "Failed to execute DataBase query with request\n"
                            + request
                            + "\nand parameter "
                            + queryParam,
                    e);
        }
        List<AttributeMap> resultsList = new ArrayList<>();
        try {
            while (queryResult.next()) {
                resultsList.add(parseResultsRow(queryResult));
            }
        } catch (SQLException | ClassCastException e) {
            throw new FetchException("Failed to parse database query results", e);
        }
        Attribute[] resultsArray = resultsList.toArray(new AttributeMap[0]);
        return (ArrayAttribute) AttributeFactory.createAttribute(resultsArray);
    }

    private AttributeMap parseResultsRow(ResultSet rs) throws SQLException, ClassCastException {
        AttributeMap resultMap = new AttributeMap();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            Object resultItem = rs.getObject(i);
            if (resultItem instanceof BigDecimal) {
                resultItem = ((BigDecimal) resultItem).doubleValue();
            }
            resultMap.addItem(metaData.getColumnName(i), resultItem);
        }
        return resultMap;
    }
}
