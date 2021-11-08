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
    public static LoanData fetch(CarBuyer buyer, Car car) throws Exceptions.CodedException {
        Logger l = LoggerFactory.getLogger();
        System.out.println("LoanDatas 1 reached ");

        HttpURLConnection rateConn;
        try {
            rateConn = (HttpURLConnection) Env.SENSO_RATE_URL.openConnection();
        } catch (IOException e) {
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("Error connecting to Senso API");
        }
        System.out.println("LoanDatas 2 reached ");
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
                        .add("vehicleKms", car.getKilometres())
                        // TODO: Understand what listPrice and downpayment are and incorporate them
                        .add("listPrice", car.getPrice())
                        .add("downpayment", car.getPrice() / 10)
                        .build();
        System.out.println("LoanDatas 3 reached ");
        try {
            OutputStreamWriter rateWriter = new OutputStreamWriter(rateConn.getOutputStream());
            rateWriter.write(rateBody.toString());
            rateWriter.close();
            System.out.println("LoanDatas 4 reached ");

        } catch (IOException e) {
            // TODO: Document this
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }
        System.out.println("LoanDatas 5 reached ");
        JsonObject rateResponse;

        try {
            if (rateConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("LoanDatas 6 reached ");
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
                System.out.println("LoanDatas 7 reached ");
            } else {
                l.error("request to the senso rate API failed");
                throw (Exceptions.CodedException) new Exceptions.FetchException();
            }
        } catch (IOException e) {
            // TODO: Document this and break it up
            throw (Exceptions.CodedException) new Exceptions.FetchException();
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
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }
        System.out.println("LoanDatas 8 reached ");

        HttpURLConnection scoreConn;

        try {
            scoreConn = (HttpURLConnection) Env.SENSO_SCORE_URL.openConnection();
        } catch (IOException e) {
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("Error connecting to Senso API");
        }
        System.out.println("LoanDatas 9 reached ");
        scoreConn.setDoOutput(true);
        scoreConn.setDoInput(true);

        scoreConn.setRequestProperty("Content-Type", "application/json");
        scoreConn.setRequestProperty("Accept", "application/json");
        System.out.println("LoanDatas 10 reached ");
        try {
            scoreConn.setRequestMethod("POST");
        } catch (java.net.ProtocolException e) {
        }
        System.out.println("LoanDatas 11 reached ");
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
        System.out.println("LoanDatas 12 reached ");
        try {
            OutputStreamWriter scoreWriter = new OutputStreamWriter(scoreConn.getOutputStream());
            scoreWriter.write(scoreBody.toString());
            scoreWriter.close();
        } catch (IOException e) {
            // TODO: Document this
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }
        System.out.println("LoanDatas 13 reached ");
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
        System.out.println("LoanDatas 14 reached ");
        String sensoScore;
        try {
            sensoScore = ((JsonString) scoreResponse.get("sensoScore")).getString();
        } catch (ClassCastException e) {
            // TODO: Document this and break it up
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }
        System.out.println("LoanDatas 15 reached ");
        return GenerateLoanUseCase.generateLoanData(
                interestRate, installment, sensoScore, loanAmount, termLength, interestSum);
    }
}
