package fetchers;

import constants.Exceptions;

import entities.Car;
import entities.CarBuyer;
import entities.LoanData;

import entitybuilder.GenerateLoanUseCase;

import logging.Logger;
import logging.LoggerFactory;

import server.Env;

import java.io.*;
import java.net.HttpURLConnection;

import javax.json.*;

public class LoanDataFetcher {
    public static LoanData fetch(CarBuyer buyer, Car car) throws Exceptions.CodedException {
        Logger l = LoggerFactory.getLogger();

        HttpURLConnection rateConn;
        try {
            rateConn = (HttpURLConnection) Env.SENSO_RATE_URL.openConnection();
        } catch (IOException e) {
            throw new Exceptions.FetchException("Error connecting to Senso API");
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
            throw new Exceptions.FetchException();
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
                throw new Exceptions.FetchException();
            }
        } catch (IOException e) {
            // TODO: Document this and break it up
            throw new Exceptions.FetchException();
        }

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
            throw new Exceptions.FetchException();
        }

        HttpURLConnection scoreConn;

        try {
            scoreConn = (HttpURLConnection) Env.SENSO_SCORE_URL.openConnection();
        } catch (IOException e) {
            throw new Exceptions.FetchException("Error connecting to Senso API");
        }

        scoreConn.setDoOutput(true);
        scoreConn.setDoInput(true);

        scoreConn.setRequestProperty("Content-Type", "application/json");
        scoreConn.setRequestProperty("Accept", "application/json");

        try {
            scoreConn.setRequestMethod("POST");
        } catch (java.net.ProtocolException e) {
        }

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
            throw new Exceptions.FetchException();
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
                throw new Exceptions.FetchException();
            }
        } catch (IOException e) {
            // TODO: Document this and break this up
            throw new Exceptions.FetchException();
        }

        String sensoScore;
        try {
            sensoScore = ((JsonString) scoreResponse.get("sensoScore")).getString();
        } catch (ClassCastException e) {
            // TODO: Document this and break it up
            throw new Exceptions.FetchException();
        }

        return GenerateLoanUseCase.generateLoanData(
                interestRate, installment, sensoScore, loanAmount, termLength, interestSum);
    }
}
