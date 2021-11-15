package fetchers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.*;
import entities.LoanData;

import entityparsers.JsonParser;

import entityparsers.Parser;
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

        AttributeMap rateRequestResult = makeRateRequest(buyer, car);

        AttributeMap scoreRequestResult = makeScoreRequest(buyer, car, (int) Math.round((double) rateRequestResult.getItem(EntityStringNames.LOAN_TERM_LENGTH).getAttribute()));

        AttributeMap loanMap = AttributeMap.combine(rateRequestResult, scoreRequestResult);

        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(EntityStringNames.LOAN_STRING, loanMap);

        return GenerateEntitiesUseCase.generateLoanData(entityMap);
    }

    public static AttributeMap makeRateRequest(CarBuyer buyer, Car car)
            throws Exceptions.CodedException {
        HttpURLConnection rateConn;

        try {
            rateConn = (HttpURLConnection) Env.SENSO_RATE_URL.openConnection();
        } catch (IOException e) {
            throw new Exceptions.FetchException("error connecting to senso rate API", e);
        }

        rateConn.setDoOutput(true);
        rateConn.setDoInput(true);

        rateConn.setRequestProperty("Content-Type", "application/json");
        rateConn.setRequestProperty("Accept", "application/json");
        try {
            rateConn.setRequestMethod("POST");
        } catch (java.net.ProtocolException e) {
            // This is safe to leave uncaught because the `setRequestMethod`
            // will only fail if the request method is not a valid request
            // method
        }
        JsonObject rateBody =
                Json.createObjectBuilder()
                        .add("loanAmount", car.getPrice())
                        .add("creditScore", buyer.getCreditScore())
                        .add("pytBudget", buyer.getBudget())
                        .add("vehicleMake", car.getMake())
                        .add("vehicleModel", car.getModel())
                        .add("vehicleYear", car.getYear())
                        .add("vehicleKms", car.getKilometres())
                        .add("listPrice", car.getPrice())
                        // TODO: make this not hardcoded
                        .add("downpayment", car.getPrice() / 10)
                        .build();

        try {
            OutputStreamWriter rateWriter = new OutputStreamWriter(rateConn.getOutputStream());
            rateWriter.write(rateBody.toString());
            rateWriter.close();
        } catch (IOException e) {
            throw new Exceptions.FetchException("error reading response from senso rate API", e);
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
                LoggerFactory.getLogger().error("senso rate API returned an error");
                throw new Exceptions.FetchException();
            }
        } catch (IOException e) {
            throw new Exceptions.FetchException("error reading senso rate API body", e);
        }

        JsonParser parser = new JsonParser(rateResponse);
        AttributeMap rateResponseMap;
        try {
            rateResponseMap = parser.parse();
        } catch (Exceptions.ParseException e) {
            throw new Exceptions.FetchException("failed to parse result from senso rate API: " + e.getMessage(), e);
        }
        Attribute[] installments = ((ArrayAttribute) rateResponseMap.getItem("installments")).getAttribute();
        rateResponseMap.addItem(EntityStringNames.LOAN_INSTALLMENT, ((AttributeMap) installments[0]).getItem("installment"));
        //TODO: remove this when we figure out why senso is sending term as a string
        rateResponseMap.addItem("term", Double.parseDouble((String) rateResponseMap.getItem("term").getAttribute()));

        return rateResponseMap;
    }

    public static AttributeMap makeScoreRequest(CarBuyer buyer, Car car, int termLength)
            throws Exceptions.CodedException {
        HttpURLConnection scoreConn;

        try {
            scoreConn = (HttpURLConnection) Env.SENSO_SCORE_URL.openConnection();
        } catch (IOException e) {
            throw new Exceptions.FetchException("error connecting to senso score API", e);
        }

        scoreConn.setDoOutput(true);
        scoreConn.setDoInput(true);

        scoreConn.setRequestProperty("Content-Type", "application/json");
        scoreConn.setRequestProperty("Accept", "application/json");
        try {
            scoreConn.setRequestMethod("POST");
        } catch (java.net.ProtocolException e) {
            // This is safe to leave uncaught because the `setRequestMethod`
            // will only fail if the request method is not a valid request
            // method
        }

        JsonObject scoreBody =
                Json.createObjectBuilder()
                        .add("remainingBalance", car.getPrice())
                        .add("creditScore", buyer.getCreditScore())
                        .add("loanAge", termLength)
                        .add("vehicleMake", car.getMake())
                        .add("vehicleModel", car.getModel())
                        .add("vehicleYear", car.getYear())
                        // TODO: Understand what carValue and loanStartDate are, and make them not
                        // hardcoded
                        .add("carValue", car.getPrice())
                        .add("loanStartDate", String.valueOf(java.time.LocalDate.now()))
                        .build();

        try {
            OutputStreamWriter scoreWriter = new OutputStreamWriter(scoreConn.getOutputStream());
            scoreWriter.write(scoreBody.toString());
            scoreWriter.close();
        } catch (IOException e) {
            throw new Exceptions.FetchException("error reading response from senso score API", e);
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
                LoggerFactory.getLogger().error("senso score API returned an error");
                throw new Exceptions.FetchException();
            }
        } catch (IOException e) {
            throw new Exceptions.FetchException("error reading senso score API body", e);
        }

        Parser parser = new JsonParser(scoreResponse);
        try {
            return parser.parse();
        } catch (Exceptions.ParseException e) {
            throw new Exceptions.FetchException("error parsing senso score API response: " + e.getMessage(), e);
        }
    }
}
