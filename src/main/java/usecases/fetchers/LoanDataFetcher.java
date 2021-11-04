package fetchers;

import constants.Exceptions;

import entities.*;
import entities.LoanData;

import entitybuilder.GenerateLoanUseCase;

import logging.Logger;
import logging.LoggerFactory;

import server.Env;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

public class LoanDataFetcher {

    private final CarBuyer buyer;
    private final Car car;

    public LoanDataFetcher(CarBuyer buyer, Car car) {
        this.buyer = buyer;
        this.car = car;
    }

    public LoanData fetch() throws Exceptions.CodedException {

        Logger l = LoggerFactory.getLogger();

        HttpURLConnection rateConn = getRateConn();

        rateBody(rateConn);

        JsonObject rateResponse = getRateResponse(l, rateConn);

        int interestRate, termLength;
        double installment, loanAmount, interestSum;
        try {
            interestRate = ((JsonNumber) rateResponse.get("interestRate")).intValue();
            installment =
                    ((JsonNumber)
                                    ((JsonObject)
                                                    ((JsonArray) rateResponse.get("installments"))
                                                            .get(0))
                                            .get("installment"))
                            .doubleValue();
            loanAmount = ((JsonNumber) rateResponse.get("capitalSum")).doubleValue();
            termLength = Integer.parseInt(((JsonString) rateResponse.get("term")).getString());
            interestSum = ((JsonNumber) rateResponse.get("interestSum")).doubleValue();
        } catch (ClassCastException e) {
            // TODO: Document this and break it up
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }

        HttpURLConnection scoreConn = getHttpURLConnection();

        scoreBody(termLength, scoreConn);

        JsonObject scoreResponse = getScoreResponse(l, scoreConn);

        String sensoScore;
        try {
            sensoScore = ((JsonString) scoreResponse.get("sensoScore")).getString();
        } catch (ClassCastException e) {
            // TODO: Document this and break it up
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }

        return GenerateLoanUseCase.generateLoanData(
                interestRate, installment, sensoScore, loanAmount, termLength, interestSum);
    }

    private void scoreBody(int termLength, HttpURLConnection scoreConn) throws Exceptions.CodedException {
        JsonObject scoreBody =
                Json.createObjectBuilder()
                        .add("remainingBalance", car.getPrice())
                        .add("creditScore", buyer.getCreditScore())
                        .add("loanAge", termLength)
                        // TODO: Pull make and model separately instead
                        .add("vehicleMake", car.getMake())
                        .add("vehicleModel", car.getModel())
                        .add("vehicleYear", 2021)
                        .build();

        try {
            OutputStreamWriter scoreWriter = new OutputStreamWriter(scoreConn.getOutputStream());
            scoreWriter.write(scoreBody.toString());
            scoreWriter.close();
        } catch (IOException e) {
            // TODO: Document this
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }
    }

    private void rateBody(HttpURLConnection rateConn) throws Exceptions.CodedException {
        JsonObject rateBody =
                Json.createObjectBuilder()
                        .add("loanAmount", car.getPrice())
                        .add("creditScore", buyer.getCreditScore())
                        .add("pytBudget", buyer.getBudget())
                        // TODO: Pull make and model separately instead
                        .add("vehicleMake", car.getMake())
                        .add("vehicleModel", car.getModel())
                        .add("vehicleYear", car.getYear())
                        // TODO: Consider allowing this to be modified
                        .add("vehicleKms", 0)
                        .build();

        try {
            OutputStreamWriter rateWriter = new OutputStreamWriter(rateConn.getOutputStream());
            rateWriter.write(rateBody.toString());
            rateWriter.close();
        } catch (IOException e) {
            // TODO: Document this
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }
    }

    private static JsonObject getScoreResponse(Logger l, HttpURLConnection scoreConn) throws Exceptions.CodedException {
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
        return scoreResponse;
    }


    private static HttpURLConnection getHttpURLConnection() throws Exceptions.CodedException {
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
        }
        return scoreConn;
    }

    private static HttpURLConnection getRateConn() throws Exceptions.CodedException {
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
        return rateConn;
    }

    private static JsonObject getRateResponse(Logger l, HttpURLConnection rateConn) throws Exceptions.CodedException {
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
        return rateResponse;
    }
}
