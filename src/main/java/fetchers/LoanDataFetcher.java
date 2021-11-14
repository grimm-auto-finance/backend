package fetchers;

import constants.Exceptions;

import entities.*;
import entities.LoanData;

import entitybuilder.GenerateLoanUseCase;

import logging.LoggerFactory;

import server.Env;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.*;

public class LoanDataFetcher {
    public static LoanData fetch(CarBuyer buyer, Car car) throws Exceptions.CodedException {

        Object[] rateRequestResult = makeRateRequest(buyer, car);

        int interestRate = (int) rateRequestResult[0];
        int termLength = (int) rateRequestResult[1];
        double installment = (double) rateRequestResult[2];
        double loanAmount = (double) rateRequestResult[3];
        double interestSum = (double) rateRequestResult[4];
        // noinspection unchecked
        List<Map<String, Double>> amortizationTable =
                (List<Map<String, Double>>) rateRequestResult[5];

        String sensoScore = makeScoreRequest(buyer, car, termLength);

        return GenerateLoanUseCase.generateLoanData(
                interestRate,
                installment,
                sensoScore,
                loanAmount,
                termLength,
                interestSum,
                amortizationTable);
    }

    public static Object[] makeRateRequest(CarBuyer buyer, Car car)
            throws Exceptions.CodedException {
        HttpURLConnection rateConn;

        try {
            rateConn = (HttpURLConnection) Env.SENSO_RATE_URL.openConnection();
        } catch (IOException e) {
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("error connecting to senso rate API", e);
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
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("error reading response from senso rate API", e);
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
                throw (Exceptions.CodedException) new Exceptions.FetchException();
            }
        } catch (IOException e) {
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("error reading senso rate API body", e);
        }

        int interestRate, termLength;
        double installment, loanAmount, interestSum;
        List<Map<String, Double>> amortizationTable = new ArrayList<>();

        try {
            interestRate = ((JsonNumber) rateResponse.get("interestRate")).intValue();
            installment =
                    ((JsonNumber)
                                    ((JsonObject)
                                                    ((JsonArray) rateResponse.get("installments"))
                                                            .get(0))
                                            .get("installment"))
                            .doubleValue();
            JsonArray installments = (JsonArray) rateResponse.get("installments");
            for (JsonValue i : installments) {
                Map<String, Double> installmentMap = new HashMap<>();
                JsonObject installmentObject = i.asJsonObject();
                for (String s : installmentObject.keySet()) {
                    installmentMap.put(s, installmentObject.getJsonNumber(s).doubleValue());
                }
                amortizationTable.add(installmentMap);
            }
            loanAmount = ((JsonNumber) rateResponse.get("capitalSum")).doubleValue();
            termLength = Integer.parseInt(((JsonString) rateResponse.get("term")).getString());
            interestSum = ((JsonNumber) rateResponse.get("interestSum")).doubleValue();
        } catch (ClassCastException e) {
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("senso rate API returned an invalid body", e);
        }

        return new Object[] {
            interestRate, termLength, installment, loanAmount, interestSum, amortizationTable
        };
    }

    public static String makeScoreRequest(CarBuyer buyer, Car car, int termLength)
            throws Exceptions.CodedException {
        HttpURLConnection scoreConn;

        try {
            scoreConn = (HttpURLConnection) Env.SENSO_SCORE_URL.openConnection();
        } catch (IOException e) {
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("error connecting to senso score API", e);
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
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("error reading response from senso score API", e);
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
                throw (Exceptions.CodedException) new Exceptions.FetchException();
            }
        } catch (IOException e) {
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("error reading senso score API body", e);
        }

        String sensoScore;

        try {
            sensoScore = ((JsonString) scoreResponse.get("sensoScore")).getString();
        } catch (ClassCastException e) {
            throw (Exceptions.CodedException)
                    new Exceptions.FetchException("senso score API returned an invalid body", e);
        }

        return sensoScore;
    }
}
