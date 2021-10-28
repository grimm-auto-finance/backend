package fetchers;

import constants.Exceptions;

import entities.*;
import entities.LoanData;

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
        LoanData loanData = new LoanData();

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
                // TODO: switch this to use Logging class once implemented
                System.out.println("Request to the Senso Rate API failed");
                throw (Exceptions.CodedException) new Exceptions.FetchException();
            }
        } catch (IOException e) {
            // TODO: Document this and break it up
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }

        try {
            loanData.setInterestRate(((JsonNumber) rateResponse.get("interestRate")).intValue());
            loanData.setInstallment(
                    ((JsonNumber)
                                    ((JsonObject)
                                                    ((JsonArray) rateResponse.get("installments"))
                                                            .get(0))
                                            .get("installment"))
                            .doubleValue());
            loanData.setLoanAmount(((JsonNumber) rateResponse.get("capitalSum")).doubleValue());
            loanData.setTermLength(
                    Integer.parseInt(((JsonString) rateResponse.get("term")).getString()));
            loanData.setInterestSum(((JsonNumber) rateResponse.get("interestSum")).doubleValue());
        } catch (ClassCastException e) {
            // TODO: Document this and break it up
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }

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

        JsonObject scoreBody =
                Json.createObjectBuilder()
                        .add("remainingBalance", car.getPrice())
                        .add("creditScore", buyer.getCreditScore())
                        .add("loanAge", loanData.getTermLength())
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
                // TODO: switch this to use Logging class once implemented
                System.out.println("Request to the Senso API failed");
                throw (Exceptions.CodedException) new Exceptions.FetchException();
            }
        } catch (IOException e) {
            // TODO: Document this and break this up
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }

        try {
            loanData.setSensoScore(((JsonString) scoreResponse.get("sensoScore")).getString());
        } catch (ClassCastException e) {
            // TODO: Document this and break it up
            throw (Exceptions.CodedException) new Exceptions.FetchException();
        }

        return loanData;
    }
}
