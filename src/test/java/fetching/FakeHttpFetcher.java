package fetching;

import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.LoanData;
import entities.TestEntityCreator;

import attributizing.AttributizeLoanDataUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeHttpFetcher implements Fetcher {

    @Override
    public AttributeMap fetch(String request) throws Exceptions.FetchException {
        if (request.contains("loanAmount")) {
            LoanData resultData = TestEntityCreator.getTestLoanData();
            AttributeMap rateMap = new AttributeMap();
            rateMap.addItem(EntityStringNames.LOAN_AMOUNT, resultData.getLoanAmount());
            rateMap.addItem(
                    EntityStringNames.LOAN_TERM_LENGTH,
                    ((Integer) resultData.getTermLength()).toString());
            rateMap.addItem(EntityStringNames.LOAN_INTEREST_SUM, resultData.getInterestSum());
            rateMap.addItem(EntityStringNames.LOAN_INSTALLMENT, resultData.getInstallment());
            rateMap.addItem(EntityStringNames.LOAN_INTEREST_RATE, resultData.getInterestRate());
            List<Map<String, Double>> amortizationTable = new ArrayList<>();
            Map<String, Double> installment = new HashMap<>();
            installment.put("installment", resultData.getInstallment());
            amortizationTable.add(installment);
            List<AttributeMap> amortizationAttMap =
                    AttributizeLoanDataUseCase.getAmortizationAttMap(amortizationTable);
            rateMap.addItem(
                    EntityStringNames.LOAN_AMORTIZATION,
                    amortizationAttMap.toArray(new Attribute[0]));
            return rateMap;
        } else if (request.contains("loanAge")) {
            LoanData resultData = TestEntityCreator.getTestLoanData();
            AttributeMap scoreMap = new AttributeMap();
            scoreMap.addItem(EntityStringNames.LOAN_SCORE, resultData.getSensoScore());
            return scoreMap;
        }
        throw new Exceptions.FetchException("sucks to suck");
    }

    @Override
    public void setFetchParam(Object param) throws Exceptions.FetchException {}
}
