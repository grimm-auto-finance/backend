package fetchers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.*;
import entities.LoanData;

import entityparsers.JsonParser;

import logging.Logger;
import logging.LoggerFactory;

import server.Env;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;

import javax.json.*;

public class LoanDataFetcher {
    public static LoanData fetch(CarBuyer buyer, Car car) throws Exceptions.CodedException {
        Logger l = LoggerFactory.getLogger();

        HttpURLConnection rateConn;
        try {
            rateConn = (HttpURLConnection) Env.SENSO_RATE_URL.openConnection();
        } catch (IOException e) {
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("Error connecting to Senso API");
        }

        rateConn.setDoOutput(true);
        rateConn.setDoInput(true);

        rateConn.setRequestProperty("Content-Type", "application/json");
        rateConn.setRequestProperty("Accept", "application/json");
        try {
            rateConn.setRequestMethod("POST");
        } catch (java.net.ProtocolException e) {
        }
        JsonObject rateBody =
                Json.createObjectBuilder()
                        .add("loanAmount", car.getPrice())
                        .add("creditScore", buyer.getCreditScore())
                        .add("pytBudget", buyer.getBudget())
                        .add("vehicleMake", car.getMake())
                        .add("vehicleModel", car.getModel())
                        .add("vehicleYear", car.getYear())
                        // TODO: Consider allowing this to be modified
                        .add("vehicleKms", 0)
                        // TODO: Understand what listPrice and downpayment are and incorporate them
                        .add("listPrice", car.getPrice())
                        .add("downpayment", car.getPrice() / 10)
                        .build();

        try {
            OutputStreamWriter rateWriter = new OutputStreamWriter(rateConn.getOutputStream());
            rateWriter.write(rateBody.toString());
            rateWriter.close();
        } catch (IOException e) {
            // TODO: Document this
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }

        JsonObject rateResponse;
        try {
            if (rateConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(rateConn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();

                JsonReader jsonReader =
                        Json.createReader(new StringReader(responseBuilder.toString()));
                rateResponse = jsonReader.readObject();
            } else {
                l.error("request to the senso rate API failed");
                throw (Exceptions.CodedException) new Exceptions.FetchException();
            }
        } catch (IOException e) {
            // TODO: Document this and break it up
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }

        l.info(rateResponse.get("term").toString());
        l.info(rateResponse.get("term").getValueType().toString());
        JsonParser parser = new JsonParser(rateResponse);
        AttributeMap rateResponseMap;
        try {
            rateResponseMap = parser.parse();
        } catch (Exceptions.ParseException e) {
            Exceptions.FetchException ex = new Exceptions.FetchException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        Attribute[] installments = ((ArrayAttribute) rateResponseMap.getItem("installments")).getAttribute();
        rateResponseMap.addItem("installment", ((AttributeMap) installments[0]).getItem("installment"));
        //TODO: remove this when we figure out why senso is sending term as a string
        rateResponseMap.addItem("term", Double.parseDouble((String) rateResponseMap.getItem("term").getAttribute()));

        HttpURLConnection scoreConn;

        try {
            scoreConn = (HttpURLConnection) Env.SENSO_SCORE_URL.openConnection();
        } catch (IOException e) {
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("Error connecting to Senso API");
        }

        scoreConn.setDoOutput(true);
        scoreConn.setDoInput(true);

        scoreConn.setRequestProperty("Content-Type", "application/json");
        scoreConn.setRequestProperty("Accept", "application/json");
        try {
            scoreConn.setRequestMethod("POST");
        } catch (java.net.ProtocolException e) {
            l.error("Failed to set scoreConn request method", e);
        }

        JsonObjectBuilder scoreBodyBuilder =
                Json.createObjectBuilder();
        scoreBodyBuilder.add("remainingBalance", car.getPrice());
        scoreBodyBuilder.add("creditScore", buyer.getCreditScore());
        //TODO: figure out why senso api is sending term as a string
        scoreBodyBuilder.add("loanAge", (int) Math.round((double) rateResponseMap.getItem("term").getAttribute()));
        scoreBodyBuilder.add("vehicleMake", car.getMake());
        scoreBodyBuilder.add("vehicleModel", car.getModel());
        scoreBodyBuilder.add("vehicleYear", car.getYear());
        // TODO: Understand what carValue and loanStartDate are, and make them not
        // hardcoded
        scoreBodyBuilder.add("carValue", car.getPrice());
        scoreBodyBuilder.add("loanStartDate", String.valueOf(java.time.LocalDate.now()));
        JsonObject scoreBody = scoreBodyBuilder.build();
        try {
            OutputStreamWriter scoreWriter = new OutputStreamWriter(scoreConn.getOutputStream());
            scoreWriter.write(scoreBody.toString());
            scoreWriter.close();
        } catch (IOException e) {
            // TODO: Document this
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }
        JsonObject scoreResponse;

        try {
            if (scoreConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(scoreConn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();

                JsonReader jsonReader =
                        Json.createReader(new StringReader(responseBuilder.toString()));
                scoreResponse = jsonReader.readObject();
            } else {
                l.error("request to the senso score API failed");
                throw (Exceptions.CodedException) new Exceptions.FetchException();
            }
        } catch (IOException e) {
            // TODO: Document this and break this up
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }

        parser = new JsonParser(scoreResponse);
        AttributeMap scoreResponseMap;
        try {
            scoreResponseMap = parser.parse();
        } catch (Exceptions.ParseException e) {
            Exceptions.FetchException ex = new Exceptions.FetchException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }

        AttributeMap loanMap = AttributeMap.combine(rateResponseMap, scoreResponseMap);
        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(EntityStringNames.LOAN_STRING, loanMap);
        return GenerateEntitiesUseCase.generateLoanData(entityMap);
    }
}
