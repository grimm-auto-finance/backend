// Layer: interfaceadapters
package fetchers;

import attributes.Attribute;

import constants.Exceptions;

public interface Fetcher {

    /**
     * Fetch information corresponding to the given request
     *
     * @param request
     * @return an Attribute representing the response to the fetch request
     * @throws Exceptions.FetchException if the fetch fails
     */
    Attribute fetch(String request) throws Exceptions.FetchException;

    void setFetchParam(Object param) throws Exceptions.FetchException;
}
