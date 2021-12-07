// layer: interfaceadapters
package entityparsers;

import attributes.AttributeMap;

import constants.Exceptions;

public interface Parser {

    AttributeMap parse() throws Exceptions.ParseException;
    void setParseObject(Object obj) throws Exceptions.ParseException;
}
