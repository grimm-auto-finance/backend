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
    this.map = parser.parse();
  }

  /**
   * Creates a Car object from the fields in jsonObject
   *
   * @return
   */
  public Car parse() throws Exceptions.ParseException {
    GenerateCarUseCase carGenerator = new GenerateCarUseCase();
    String make, model;
    int year;
    double price;
    try {
      // TODO: should throw an Exception if any of these are null
      AttributeMap carMap = (AttributeMap) map.getItem(EntityStringNames.CAR_STRING);
      make = (String) carMap.getItem(EntityStringNames.CAR_MAKE).getAttribute();
      model = (String) carMap.getItem(EntityStringNames.CAR_MODEL).getAttribute();
      year = (int) Math.round((Double) carMap.getItem(EntityStringNames.CAR_YEAR).getAttribute());
      price = (double) carMap.getItem(EntityStringNames.CAR_PRICE).getAttribute();
    } catch (ClassCastException e) {
      Exceptions.ParseException ex = new Exceptions.ParseException(e.getMessage());
      ex.setStackTrace(e.getStackTrace());
      throw ex;
    }
    // TODO: Support optional inclusion of addons?
    return carGenerator.GenerateCarUseCase(price, make, model, year);
  }
}
