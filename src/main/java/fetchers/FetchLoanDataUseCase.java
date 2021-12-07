// layer: usecases
package fetchers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.*;

import entitypackagers.Package;
import entitypackagers.Packager;

public class FetchLoanDataUseCase {
    private final Fetcher rateFetcher;
    private final Fetcher scoreFetcher;
    private final Packager packager;

    /**
     * Constructs a new FetchLoanDataUseCase to use the given Fetchers and Packager.
     *
     * @param rateFetcher the Fetcher to be used for making laon rate information requests
     * @param scoreFetcher the Fetcher to be used for making loan Senso Score information requests
     * @param packager the Packager to be used for the rate and loan request bodies
     */
    public FetchLoanDataUseCase(Fetcher rateFetcher, Fetcher scoreFetcher, Packager packager) {
        this.rateFetcher = rateFetcher;
        this.scoreFetcher = scoreFetcher;
        this.packager = packager;
    }

    /**
     * Make a request using this FetchLoanDataUseCase's Fetchers and the given CarBuyer and Car.
     * Return a LoanData constructed using the responses from the Fetchers
     *
     * @param buyer the CarBuyer who is getting the loan
     * @param car the Car being purchased with the loan
     * @return a LoanData constructed using the responses from the Fetchers
     * @throws Exceptions.CodedException if any step of the fetching process fails
     */
    public LoanData getLoanData(CarBuyer buyer, Car car, int loopMax)
            throws Exceptions.CodedException {

        AttributeMap rateRequestResult = makeRateRequest(buyer, car, 0);
        rateRequestResult.addItem(
                EntityStringNames.LOAN_ADD_ON_BUDGET, getAddOnBudgetLoop(buyer, car, loopMax));
        AttributeMap scoreRequestResult;
        try {
            scoreRequestResult =
                    makeScoreRequest(
                            buyer,
                            car,
                            (int)
                                    rateRequestResult
                                            .getItem(EntityStringNames.LOAN_TERM_LENGTH)
                                            .getAttribute());
        } catch (ClassCastException | NullPointerException e) {
            String message = "Loan term length of wrong type";
            throw new Exceptions.FetchException(message, e);
        }
        AttributeMap loanMap = AttributeMap.combine(rateRequestResult, scoreRequestResult);

        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(EntityStringNames.LOAN_STRING, loanMap);

        return GenerateEntitiesUseCase.generateLoanData(entityMap);
    }

    private double getAddOnBudgetLoop(CarBuyer buyer, Car car, int loopMax)
            throws Exceptions.CodedException {
        double addOnBudget = 0;
        try {
            int numLoops = 0;
            boolean inf = loopMax == -1;
            while (inf || numLoops < loopMax) {
                double priceIncrement = addOnBudget + (car.getTotalPrice() * .05);
                makeRateRequest(buyer, car, priceIncrement);
                addOnBudget = priceIncrement;
                numLoops++;
            }
        } catch (Exceptions.FetchException e) {
            return addOnBudget;
        }
        return addOnBudget;
    }

    private AttributeMap makeRateRequest(CarBuyer buyer, Car car, double priceModifier)
            throws Exceptions.CodedException {
        Package rateBody;
        rateBody = getRateBody(buyer, car, priceModifier);
        AttributeMap rateResponseMap;
        rateFetcher.setFetchParam("POST");
        rateResponseMap = (AttributeMap) rateFetcher.fetch(rateBody.toString());
        addInstallments(rateResponseMap);

        return rateResponseMap;
    }

    private void addInstallments(AttributeMap rateResponseMap) {
        Attribute[] installments =
                ((ArrayAttribute) rateResponseMap.getItem("installments")).getAttribute();
        rateResponseMap.addItem(
                EntityStringNames.LOAN_INSTALLMENT,
                ((AttributeMap) installments[0]).getItem("installment"));
        // for some reason, senso sends term length back as a string instead of a number
        rateResponseMap.addItem(
                "term", Integer.parseInt((String) rateResponseMap.getItem("term").getAttribute()));
    }

    private Package getRateBody(CarBuyer buyer, Car car, double priceModifier)
            throws Exceptions.PackageException {
        AttributeMap rateMap = new AttributeMap();
        rateMap.addItem("loanAmount", car.getTotalPrice() + priceModifier);
        rateMap.addItem("creditScore", buyer.getCreditScore());
        rateMap.addItem("pytBudget", buyer.getBudget());
        rateMap.addItem("vehicleMake", car.getMake());
        rateMap.addItem("vehicleModel", car.getModel());
        rateMap.addItem("vehicleYear", car.getYear());
        rateMap.addItem("vehicleKms", car.getKilometres());
        rateMap.addItem("listPrice", car.getPrice());
        rateMap.addItem("downpayment", buyer.getDownPayment());
        return packager.writePackage(rateMap);
    }

    private AttributeMap makeScoreRequest(CarBuyer buyer, Car car, int termLength)
            throws Exceptions.CodedException {
        Package scoreBody = getScoreBody(buyer, car, termLength);
        scoreFetcher.setFetchParam("POST");
        return (AttributeMap) scoreFetcher.fetch(scoreBody.toString());
    }

    private Package getScoreBody(CarBuyer buyer, Car car, int termLength)
            throws Exceptions.PackageException {

        AttributeMap scoreMap = new AttributeMap();
        scoreMap.addItem("remainingBalance", car.getTotalPrice());
        scoreMap.addItem("creditScore", buyer.getCreditScore());
        scoreMap.addItem("loanAge", termLength);
        scoreMap.addItem("vehicleMake", car.getMake());
        scoreMap.addItem("vehicleModel", car.getModel());
        scoreMap.addItem("vehicleYear", car.getYear());
        scoreMap.addItem("carValue", car.getTotalPrice());
        scoreMap.addItem("loanStartDate", String.valueOf(java.time.LocalDate.now()));
        return packager.writePackage(scoreMap);
    }
}
