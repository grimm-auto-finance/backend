package entities;

import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;

public class CarFactory implements EntityFactory {

  public Car getEntity(AttributeMap map) throws Exceptions.FactoryException {
      String make, model;
      int year;
      double price;
      try {
          make = (String) map.getItem(EntityStringNames.CAR_MAKE).getAttribute();
          model = (String) map.getItem(EntityStringNames.CAR_MODEL).getAttribute();
          year =
                  (int)
                          Math.round(
                                  (Double)
                                          map.getItem(EntityStringNames.CAR_YEAR)
                                                  .getAttribute());
          price = (double) map.getItem(EntityStringNames.CAR_PRICE).getAttribute();
      } catch (ClassCastException | NullPointerException e) {
          Exceptions.FactoryException ex = new Exceptions.FactoryException(e.getMessage());
          ex.setStackTrace(e.getStackTrace());
          throw ex;
      }
      // TODO: Support optional inclusion of addons?
      return new Car(price, make, model, year);
  }
}
