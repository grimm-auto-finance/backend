package fetchers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeFactory;
import attributes.AttributeMap;

import constants.Exceptions;
import constants.Exceptions.FetchException;


import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseFetcher implements Fetcher {

    private final DataBase database;
    private Object queryParam;

    public DataBaseFetcher(DataBase database) {
        this.database = database;
    }

    public void setFetchParam(Object queryParam) {
        this.queryParam = queryParam;
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
                resultsList.add(parseResultsRow(queryResult));
            }
        } catch (SQLException | ClassCastException e) {
            throw new FetchException("Failed to parse database query results: " + e.getMessage(), e);
        }
        Attribute[] resultsArray = resultsList.toArray(new AttributeMap[0]);
        return (ArrayAttribute) AttributeFactory.createAttribute(resultsArray);
    }

    private AttributeMap parseResultsRow(ResultSet rs) throws SQLException, ClassCastException {
        AttributeMap resultMap = new AttributeMap();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 2; i < metaData.getColumnCount(); i++) {
            Object resultItem = rs.getObject(i);
            if (resultItem instanceof BigDecimal) {
                resultItem = ((BigDecimal) resultItem).doubleValue();
            } else if (resultItem instanceof Integer) {
                resultItem = ((Integer) resultItem).doubleValue();
            }
            resultMap.addItem(metaData.getColumnName(i), resultItem);
        }
        return resultMap;
    }
}
