// layer: controllers
package routes;

import attributes.AttributeMap;
import attributes.IntAttribute;

import com.sun.net.httpserver.HttpExchange;

import constants.EntityStringNames;
import constants.Exceptions;
import constants.Exceptions.CodedException;

import entities.*;

import fetching.FetchLoanDataUseCase;
import fetching.Fetcher;
import fetching.HTTPFetcher;

import logging.Logger;

import packaging.Package;
import packaging.PackageEntityUseCase;
import packaging.Packager;

import parsing.Parser;

import java.io.*;
import java.net.URL;

/** The Route handling the `/loan` route which allows users to fetch information about a loan. */
public class Loan extends Route {

    private final URL SENSO_RATE_URL, SENSO_SCORE_URL;

    /** Returns the URL context for this Route: /loan */
    @Override
    public String getContext() {
        return "/loan";
    }

    /**
     * Constructs the AddOns route with the given instance attributes
     *
     * @param SENSO_RATE_URL the URL to make Senso Rate requests to
     * @param SENSO_SCORE_URL the URL to make Senso score requests to
     * @param logger the logger to log results/errors to
     * @param parser the parser to use for input data
     * @param packager the packager to use for output data
     */
    public Loan(
            URL SENSO_RATE_URL,
            URL SENSO_SCORE_URL,
            Logger logger,
            Parser parser,
            Packager packager) {
        super(logger, parser, packager);
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
        parser.setParseObject(is);
        AttributeMap entitiesMap = parser.parse();
        int maxLoopRetries;
        // default to no looping for add-on budget if no parameter is given
        try {
            IntAttribute maxLoopAttribute =
                    (IntAttribute) entitiesMap.getItem(EntityStringNames.LOAN_LOOP_MAX);
            maxLoopRetries = maxLoopAttribute.getAttribute();
        } catch (NullPointerException e) {
            maxLoopRetries = 0;
        }
        AttributeMap carMap = (AttributeMap) entitiesMap.getItem(EntityStringNames.CAR_STRING);
        carMap.addItem(EntityStringNames.CAR_ID, 0);
        Car car = GenerateEntitiesUseCase.generateCar(entitiesMap);
        CarBuyer buyer = GenerateEntitiesUseCase.generateCarBuyer(entitiesMap);
        respond(t, 200, getResponse(buyer, car, maxLoopRetries).getBytes());
    }

    private String getResponse(CarBuyer buyer, Car car, int loopMax) throws CodedException {
        LoanData loanData = getLoanData(buyer, car, loopMax);
        return getEntitiesPackage(loanData).toString();
    }

    private Package getEntitiesPackage(LoanData loanData) throws Exceptions.PackageException {
        PackageEntityUseCase packageEntity = new PackageEntityUseCase(packager);
        return packageEntity.writeEntity(loanData);
    }

    private LoanData getLoanData(CarBuyer buyer, Car car, int loopMax) throws CodedException {
        Fetcher rateFetcher = new HTTPFetcher(SENSO_RATE_URL, logger);
        Fetcher scoreFetcher = new HTTPFetcher(SENSO_SCORE_URL, logger);
        FetchLoanDataUseCase fetchLoanData =
                new FetchLoanDataUseCase(rateFetcher, scoreFetcher, packager);
        return fetchLoanData.getLoanData(buyer, car, loopMax);
    }
}
