// layer: interfaceadapters
package fetching;

import attributes.Attribute;

import constants.Exceptions;

public interface Fetcher {

    /**
     * Fetch information corresponding to the given request
     *
     * @param request the request to be made
     * @return an Attribute representing the response to the fetch request
     * @throws Exceptions.FetchException if the fetch fails
     */
    Attribute fetch(String request) throws Exceptions.FetchException;

    /**
     * Sets the value of a parameter associated with fetch requests
     * The meaning of this parameter varies by the type of fetcher
     * @param param the parameter to be set
     * @throws Exceptions.FetchException if setting the parameter fails
     */
    void setFetchParam(Object param) throws Exceptions.FetchException;
}
