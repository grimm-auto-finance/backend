package entities;

import static org.junit.jupiter.api.Assertions.fail;

import attributes.Attribute;
import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entitypackagers.AttributizeLoanDataUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEntityCreator {

    public static Car getTestCar() {
        return getTestCar(50000.0, "Honda", "Civic", "image", 2020, 100.0, new AttributeMap(), 3);
    }

    public static Car getTestCar(int id) {
        return getTestCar(50000.0, "Honda", "Civic", "image", 2020, 100.0, new AttributeMap(), id);
    }

    public static Car getTestCar(
            double price,
            String make,
            String model,
            String image,
            int year,
            double kms,
            AttributeMap addOns,
            int id) {
        Car car = null;
        AttributeMap carMap = new AttributeMap();
        carMap.addItem(EntityStringNames.CAR_PRICE, price);
        carMap.addItem(EntityStringNames.CAR_MAKE, make);
        carMap.addItem(EntityStringNames.CAR_MODEL, model);
        carMap.addItem(EntityStringNames.CAR_IMAGE, image);
        carMap.addItem(EntityStringNames.CAR_YEAR, year);
        carMap.addItem(EntityStringNames.CAR_KILOMETRES, kms);
        carMap.addItem(EntityStringNames.ADD_ON_STRING, addOns);
        carMap.addItem(EntityStringNames.CAR_ID, id);
        try {
            car = CarFactory.getEntity(carMap);
        } catch (Exceptions.FactoryException e) {
            fail("Factory creation of Car failed");
        }
        return car;
    }

    public static CarBuyer getTestBuyer() {
        CarBuyer buyer = null;
        AttributeMap buyerMap = new AttributeMap();
        buyerMap.addItem(EntityStringNames.BUYER_BUDGET, 30000.5);
        buyerMap.addItem(EntityStringNames.BUYER_CREDIT, 750);
        buyerMap.addItem(EntityStringNames.BUYER_DOWNPAYMENT, 15000.0);
        try {
            buyer = CarBuyerFactory.getEntity(buyerMap);
        } catch (Exceptions.FactoryException e) {
            fail("Factory creation of CarBuyer failed: " + e.getMessage());
        }
        return buyer;
    }

    public static AddOn getTestAddOn() {
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

    public static LoanData getTestLoanData() {
        LoanData loan = null;
        AttributeMap loanMap = new AttributeMap();
        loanMap.addItem(EntityStringNames.LOAN_AMOUNT, 1.25);
        loanMap.addItem(EntityStringNames.LOAN_INSTALLMENT, 500.25);
        loanMap.addItem(EntityStringNames.LOAN_SCORE, "Medium");
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_RATE, 50000.0);
        loanMap.addItem(EntityStringNames.LOAN_INTEREST_SUM, 36.0);
        loanMap.addItem(EntityStringNames.LOAN_TERM_LENGTH, 200);
        List<Map<String, Double>> amortizationTable = new ArrayList<>();
        Map<String, Double> installment = new HashMap<>();
        installment.put("installment", 500.25);
        amortizationTable.add(installment);
        List<AttributeMap> amortizationAttMap =
                AttributizeLoanDataUseCase.getAmortizationAttMap(amortizationTable);
        loanMap.addItem(
                EntityStringNames.LOAN_AMORTIZATION, amortizationAttMap.toArray(new Attribute[0]));
        try {
            loan = LoanDataFactory.getEntity(loanMap);
        } catch (Exceptions.FactoryException e) {
            fail("Factory creation of LoanData failed");
        }
        return loan;
    }
}
