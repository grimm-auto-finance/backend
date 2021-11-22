package fetchers;

import attributes.Attribute;
import attributes.AttributeMap;
import constants.Exceptions;

import java.net.URL;

public interface Fetcher {

  Attribute fetch(String request)
      throws Exceptions.FetchException;

  void setFetchParam(Object param) throws Exceptions.FetchException;
}
