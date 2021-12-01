// Layer: frameworksanddrivers
package fetchers;

import attributes.AttributeMap;

import constants.Exceptions;

import entityparsers.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class HTTPFetcher implements Fetcher {

    private final URL connectionURL;
    private String requestMethod;

    /**
     * Constructs a new HTTPFetcher to make requests to the given connectionURL
     *
     * @param connectionURL the URL that requests will be sent to/received from
     */
    public HTTPFetcher(URL connectionURL) {
        this.connectionURL = connectionURL;
    }

    /**
     * Sets the Fetch parameter of this Fetcher With HTTPFetcher, the Fetch Parameter is the request
     * method (ex. POST)
     *
     * @param param an Object (String) containing the fetch request method
     * @throws Exceptions.FetchException if param is not a String
     */
    public void setFetchParam(Object param) throws Exceptions.FetchException {
        if (!(param instanceof String)) {
            throw new Exceptions.FetchException("Request parameter of invalid type");
        }
        this.requestMethod = (String) param;
    }

    /**
     * Make a request to connectionURL using the request method set with setFetchParam
     *
     * @param request the request to be sent to connectionURL
     * @return an AttributeMap representing the response from connectionURL
     * @throws Exceptions.FetchException if the HTTP request fails
     */
    public AttributeMap fetch(String request) throws Exceptions.FetchException {
        JsonObject httpResponse;
        httpResponse = makeRequest(request);
        return parseResponse(httpResponse);
    }

    private AttributeMap parseResponse(JsonObject httpResponse) throws Exceptions.FetchException {
        JsonParser parser = new JsonParser(httpResponse);
        AttributeMap responseMap;
        try {
            responseMap = parser.parse();
        } catch (Exceptions.ParseException e) {
            throw new Exceptions.FetchException(
                    "Error parsing HTTP response from " + connectionURL, e);
        }
        return responseMap;
    }

    private JsonObject makeRequest(String request) throws Exceptions.FetchException {
        JsonObject httpResponse;
        try {
            HttpURLConnection connection = getHTTPConnection();
            OutputStreamWriter requestWriter = new OutputStreamWriter(connection.getOutputStream());
            requestWriter.write(request);
            requestWriter.close();
            httpResponse = getHTTPResponse(connection);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                String message =
                        connection.getRequestMethod()
                                + "request to "
                                + connectionURL
                                + " failed:\nMessage: "
                                + httpResponse.getString("message")
                                + "\nError: "
                                + httpResponse.getString("error");
                throw new IOException(message);
            }
        } catch (IOException e) {
            throw new Exceptions.FetchException("Error making HTTP Request to " + connectionURL, e);
        }
        return httpResponse;
    }

    private HttpURLConnection getHTTPConnection() throws IOException {
        HttpURLConnection conn;
        conn = (HttpURLConnection) connectionURL.openConnection();

        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        if (requestMethod == null) {
            throw new IOException("No request method set");
        }
        try {
            conn.setRequestMethod(requestMethod);
        } catch (java.net.ProtocolException e) {
            // This is safe to leave uncaught because the `setRequestMethod`
            // will only fail if the request method is not a valid request
            // method
        }
        return conn;
    }

    private JsonObject getHTTPResponse(HttpURLConnection connection) throws IOException {
        JsonObject response;
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        reader.close();

        JsonReader jsonReader = Json.createReader(new StringReader(responseBuilder.toString()));
        response = jsonReader.readObject();
        return response;
    }
}
