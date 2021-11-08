package entityparsers;

import attributes.AttributeMap;

import constants.EntityStringNames;
import constants.Exceptions;

import entities.Car;

import entitybuilder.GenerateCarUseCase;

public class ParseCarUseCase {
    private final AttributeMap map;

    /**
     * Constructs a new ParseCarUseCase to create a Car using the given Parser
     *
     * @param parser
     */
    public ParseCarUseCase(Parser parser) throws Exceptions.ParseException {
        System.out.println("CarParser 1 is reached!!!");
        this.map = parser.parse();
        System.out.println("CarParser 2 is reached!!!");
    }

    /**
     * Creates a Car object from the fields in jsonObject
     *
     * @return
     */
    public Car parse() throws Exceptions.ParseException {
        System.out.println("CarParser 3 is reached!!!");
        GenerateCarUseCase carGenerator = new GenerateCarUseCase();
        System.out.println("CarParser 4 is reached!!!");
        String make, model;
        System.out.println("CarParser 5 is reached!!!");
        int year;
        System.out.println("CarParser 6 is reached!!!");
        double price;
        System.out.println("CarParser 7 is reached!!!");
        double kilometres;
        System.out.println("CarParser 8 is reached!!!");
        try {
            System.out.println("CarParser 9 is reached!!!");
            AttributeMap carMap = (AttributeMap) map.getItem(EntityStringNames.CAR_STRING);
            System.out.println("CarParser 10 is reached!!!");
            make = (String) carMap.getItem(EntityStringNames.CAR_MAKE).getAttribute();
            System.out.println("CarParser 11 is reached!!!, so make is:" + make);
            model = (String) carMap.getItem(EntityStringNames.CAR_MODEL).getAttribute();
            System.out.println("CarParser 12 is reached!!!, so model is:" + model);
            year =
                    (int)
                            Math.round(
                                    (Double)
                                            carMap.getItem(EntityStringNames.CAR_YEAR)
                                                    .getAttribute());
            System.out.println("CarParser 13 is reached!!!, so year is:" + year);
            price = (double) carMap.getItem(EntityStringNames.CAR_PRICE).getAttribute();
            System.out.println("CarParser 14 is reached!!!, so Price is:" + price);
            kilometres = (double) carMap.getItem(EntityStringNames.CAR_KILOMETRES).getAttribute();

            System.out.println("CarParser 15 is reached!!!, so Kilometres are:" + kilometres);
        } catch (ClassCastException | NullPointerException e) {
            System.out.println("CarParser 16 is reached!!!");
            Exceptions.ParseException ex = new Exceptions.ParseException(e.getMessage());
            System.out.println("CarParser 17 is reached!!!");
            ex.setStackTrace(e.getStackTrace());
            System.out.println("CarParser 18 is reached!!!");
            throw ex;
        }
        // TODO: Support optional inclusion of addons?
        System.out.println("CarParser 19 is reached!!!");
        return carGenerator.GenerateCarUseCase(kilometres, price, make, model, year);
    }
}
