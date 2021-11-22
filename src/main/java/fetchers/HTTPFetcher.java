package fetchers;

import attributes.AttributeMap;
import constants.Exceptions;
import entityparsers.JsonParser;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPFetcher implements Fetcher {

    private final URL connectionURL;
    private String requestMethod;

    public HTTPFetcher(URL connectionURL) {
        this.connectionURL = connectionURL;
    }

    public void setFetchParam(Object param) throws Exceptions.FetchException {
        if (!(param instanceof String)) {
            throw new Exceptions.FetchException("Request parameter of invalid type");
        }
        this.requestMethod = (String) param;
    }

    public AttributeMap fetch(String request) throws Exceptions.FetchException {
        JsonObject httpResponse;
        try {
            HttpURLConnection connection = getHTTPConnection();
            OutputStreamWriter requestWriter = new OutputStreamWriter(connection.getOutputStream());
            requestWriter.write(request);
            requestWriter.close();
            httpResponse = getHTTPResponse(connection);
        } catch (IOException e) {
            throw new Exceptions.FetchException("Error making HTTP Request to " + connectionURL, e);
        }
        JsonParser parser = new JsonParser(httpResponse);
        AttributeMap responseMap;
        try {
            responseMap = parser.parse();
        } catch (Exceptions.ParseException e) {
            throw new Exceptions.FetchException("Error parsing HTTP response from " + connectionURL, e);
        }
        return responseMap;
    }

    private HttpURLConnection getHTTPConnection()
            throws IOException {
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
