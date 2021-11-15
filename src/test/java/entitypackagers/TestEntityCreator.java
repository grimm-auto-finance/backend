package entitypackagers;

import static org.junit.jupiter.api.Assertions.fail;

import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEntityCreator {

    static Car getTestCar() {
        Car car = null;
        AttributeMap carMap = new AttributeMap();
        carMap.addItem(EntityStringNames.CAR_PRICE, 50000.0);
        carMap.addItem(EntityStringNames.CAR_MAKE, "Honda");
        carMap.addItem(EntityStringNames.CAR_MODEL, "Civic");
        carMap.addItem(EntityStringNames.CAR_YEAR, 2020.0);
        carMap.addItem(EntityStringNames.CAR_KILOMETRES, 100.0);
        carMap.addItem(EntityStringNames.ADD_ON_STRING, new AttributeMap());
        // AttributeFactory.createAttribute(new Attribute[0]));
        try {
            car = CarFactory.getEntity(carMap);
        } catch (Exceptions.FactoryException e) {
            fail("Factory creation of Car failed");
        }
        return car;
    }

    static CarBuyer getTestBuyer() {
        CarBuyer buyer = null;
        AttributeMap buyerMap = new AttributeMap();
        buyerMap.addItem(EntityStringNames.BUYER_BUDGET, 30000.5);
        buyerMap.addItem(EntityStringNames.BUYER_CREDIT, 750.0);
        try {
            buyer = CarBuyerFactory.getEntity(buyerMap);
        } catch (Exceptions.FactoryException e) {
            fail("Factory creation of CarBuyer failed");
        }
        return buyer;
    }

    static AddOn getTestAddOn() {
        AddOn addOn = null;
        AttributeMap addOnMap = new AttributeMap();
        addOnMap.addItem(EntityStringNames.ADD_ON_NAME, "Rust proofing");
        addOnMap.addItem(EntityStringNames.ADD_ON_PRICE, 1000.0);
        addOnMap.addItem(EntityStringNames.ADD_ON_DESCRIPTION, "no rust allowed!");
        try {
            addOn = AddOnFactory.getEntity(addOnMap);
        } catch (Exceptions.FactoryException e) {
            fail("Factory creation of AddOn failed");
        }
        return addOn;
    }

    static LoanData getTestLoanData() {
        LoanData loan = null;
        AttributeMap loanMap = new AttributeMap();
        loanMap.addItem(EntityStringNames.LOAN_AMOUNT, 1.25);
        loanMap.addItem(EntityStringNames.LOAN_INSTALLMENT, 500.25);
        loanMap.addItem(EntityStringNames.LOAN_SCORE, "Medium");
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_RATE, 50000.0);
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_SUM, 36.0);
        loanMap.addItem(EntityStringNames.LOAN_TERM_LENGTH, 200.25);
        List<Map<String, Double>> amortizationTable = new ArrayList<>();
        Map<String, Double> installment = new HashMap<>();
        installment.put("Test String", 5.5);
        amortizationTable.add(installment);
        List<AttributeMap> ammortizationAttMap =
                AttributizeLoanDataUseCase.getAmortizationAttMap(amortizationTable);
        loanMap.addItem(
                EntityStringNames.LOAN_AMORTIZATION, ammortizationAttMap.toArray(new Attribute[0]));
        try {
            loan = LoanDataFactory.getEntity(loanMap);
        } catch (Exceptions.FactoryException e) {
            fail("Factory creation of LoanData failed");
        }
        return loan;
    }
}
