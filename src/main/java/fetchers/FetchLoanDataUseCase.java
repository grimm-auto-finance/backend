package fetchers;

import attributes.ArrayAttribute;
import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.*;

import javax.json.*;

public class FetchLoanDataUseCase {
    private final Fetcher rateFetcher;
    private final Fetcher scoreFetcher;

    public FetchLoanDataUseCase(Fetcher rateFetcher, Fetcher scoreFetcher) {
        this.rateFetcher = rateFetcher;
        this.scoreFetcher = scoreFetcher;
    }

    public LoanData fetch(CarBuyer buyer, Car car) throws Exceptions.CodedException {

        AttributeMap rateRequestResult = makeRateRequest(buyer, car);

        AttributeMap scoreRequestResult =
                makeScoreRequest(
                        buyer,
                        car,
                        (int)
                                Math.round(
                                        (double)
                                                rateRequestResult
                                                        .getItem(EntityStringNames.LOAN_TERM_LENGTH)
                                                        .getAttribute()));

        AttributeMap loanMap = AttributeMap.combine(rateRequestResult, scoreRequestResult);

        AttributeMap entityMap = new AttributeMap();
        entityMap.addItem(EntityStringNames.LOAN_STRING, loanMap);

        return GenerateEntitiesUseCase.generateLoanData(entityMap);
    }

    public AttributeMap makeRateRequest(CarBuyer buyer, Car car)
            throws Exceptions.FetchException {
        JsonObject rateBody = getRateBody(buyer, car);
        AttributeMap rateResponseMap;
        rateResponseMap = rateFetcher.fetch(rateBody.toString());
        addInstallments(rateResponseMap);

        return rateResponseMap;
    }

    private void addInstallments(AttributeMap rateResponseMap) {
        Attribute[] installments =
                ((ArrayAttribute) rateResponseMap.getItem("installments")).getAttribute();
        rateResponseMap.addItem(
                EntityStringNames.LOAN_INSTALLMENT,
                ((AttributeMap) installments[0]).getItem("installment"));
        // TODO: remove this when we figure out why senso is sending term as a string
        rateResponseMap.addItem(
                "term",
                Double.parseDouble((String) rateResponseMap.getItem("term").getAttribute()));
    }

    private JsonObject getRateBody(CarBuyer buyer, Car car) {
        return Json.createObjectBuilder()
                .add("loanAmount", car.getPrice())
                .add("creditScore", buyer.getCreditScore())
                .add("pytBudget", buyer.getBudget())
                .add("vehicleMake", car.getMake())
                .add("vehicleModel", car.getModel())
                .add("vehicleYear", car.getYear())
                .add("vehicleKms", car.getKilometres())
                .add("listPrice", car.getPrice())
                // TODO: make this not hardcoded
                .add("downpayment", buyer.getDownPayment())
                .build();
    }

    public AttributeMap makeScoreRequest(CarBuyer buyer, Car car, int termLength)
            throws Exceptions.CodedException {
        JsonObject scoreBody = getScoreBody(buyer, car, termLength);
        return scoreFetcher.fetch(scoreBody.toString());
    }

    private JsonObject getScoreBody(
            CarBuyer buyer, Car car, int termLength) {

        return Json.createObjectBuilder()
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
    }
}
