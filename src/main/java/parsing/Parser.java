// layer: interfaceadapters
package parsing;

import attributes.AttributeMap;

import constants.Exceptions;

/** An interface for classes able to read things into AttributeMaps */
public interface Parser {

    /** Parse an item and return its AttributeMap representation */
    AttributeMap parse() throws Exceptions.ParseException;

    /**
     * Sets the object that this Parser will parse
     *
     * @param obj the object to be parsed
     * @throws Exceptions.ParseException if the type of obj is incompatible with this Parser
     */
    void setParseObject(Object obj) throws Exceptions.ParseException;
}
