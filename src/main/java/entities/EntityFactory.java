package entities;

import attributes.AttributeMap;
import constants.Exceptions;

public interface EntityFactory {

  Entity getEntity(AttributeMap map) throws Exceptions.FactoryException;
}
