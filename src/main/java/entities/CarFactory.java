package entities;

import attributes.ArrayAttribute;
import attributes.AttributeMap;
import constants.EntityStringNames;
import constants.Exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarFactory {

  public static Car getEntity(AttributeMap map) throws Exceptions.FactoryException {
      String make, model;
      int year;
      double price;
      Map<String, AddOn> addOnMap;
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
          ArrayAttribute addOnArray = (ArrayAttribute) map.getItem(EntityStringNames.ADD_ON_STRING);
          List<AddOn> addOnList = AddOnFactory.getEntities(addOnArray);
          addOnMap = new HashMap<>();
          for (AddOn a : addOnList) {
              addOnMap.put(a.getName(), a);
          }
      } catch (ClassCastException | NullPointerException e) {
          Exceptions.FactoryException ex = new Exceptions.FactoryException(e.getMessage());
          ex.setStackTrace(e.getStackTrace());
          throw ex;
      }
      return new Car(price, make, model, year, addOnMap);
  }
}
