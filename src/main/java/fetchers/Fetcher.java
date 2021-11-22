package fetchers;

import attributes.AttributeMap;
import constants.Exceptions;

import java.net.URL;

public interface Fetcher {

  AttributeMap fetch(String request)
      throws Exceptions.FetchException;
}
