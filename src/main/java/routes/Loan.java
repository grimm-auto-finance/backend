package routes;

import attributes.AttributeMap;

import com.sun.net.httpserver.HttpExchange;

import constants.EntityStringNames;
import constants.Exceptions;
import constants.Exceptions.CodedException;

import entities.*;

import entitypackagers.AttributizeLoanDataUseCase;
import entitypackagers.JsonPackage;
import entitypackagers.JsonPackager;

import entityparsers.ParseJsonUseCase;

import fetchers.FetchLoanDataUseCase;
import fetchers.Fetcher;
import fetchers.HTTPFetcher;

import logging.Logger;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/** The Route handling the `/loan` route which allows users to fetch information about a loan. */
public class Loan extends Route {

    private final URL SENSO_RATE_URL, SENSO_SCORE_URL;

    @Override
    public String getContext() {
        return "/loan";
    }

    public Loan(URL SENSO_RATE_URL, URL SENSO_SCORE_URL, Logger logger) {
        super(logger);
        this.SENSO_RATE_URL = SENSO_RATE_URL;
        this.SENSO_SCORE_URL = SENSO_SCORE_URL;
    }

    /**
     * The post method for the `/loan` route. Takes in an HttpExchange containing data for a Car and
     * CarBuyer, and sends back the Car, CarBuyer, and LoanData returned by the Senso API.
     *
     * @param t the httpexchange that this method must handle
     */
    @Override
    protected void post(HttpExchange t) throws CodedException {
        InputStream is = t.getRequestBody();
        ParseJsonUseCase parseInput = new ParseJsonUseCase();
        AttributeMap entitiesMap = parseInput.parseJson(is);
        AttributeMap carMap = (AttributeMap) entitiesMap.getItem(EntityStringNames.CAR_STRING);
        carMap.addItem(EntityStringNames.CAR_ID, 0);
        Car car = GenerateEntitiesUseCase.generateCar(entitiesMap);
        CarBuyer buyer = GenerateEntitiesUseCase.generateCarBuyer(entitiesMap);
        respond(t, 200, getResponse(buyer, car).getBytes());
    }

    private String getResponse(CarBuyer buyer, Car car) throws CodedException {
        LoanData loanData = getLoanData(buyer, car);
        JsonPackage entitiesPackage = getLoanPackage(loanData);
        return entitiesPackage.toString();
    }

    private JsonPackage getLoanPackage(LoanData loanData)
            throws Exceptions.PackageException {
        JsonPackager packager = new JsonPackager();
        AttributizeLoanDataUseCase loanDataAttributizer = new AttributizeLoanDataUseCase(loanData);
        return packager.writePackage(loanDataAttributizer.attributizeEntity());
    }

    private LoanData getLoanData(CarBuyer buyer, Car car) throws CodedException {
        Fetcher rateFetcher = new HTTPFetcher(SENSO_RATE_URL);
        Fetcher scoreFetcher = new HTTPFetcher(SENSO_SCORE_URL);
        FetchLoanDataUseCase fetchLoanData =
                new FetchLoanDataUseCase(rateFetcher, scoreFetcher, new JsonPackager());
        return fetchLoanData.getLoanData(buyer, car);
    }
}
