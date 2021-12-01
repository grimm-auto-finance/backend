package entitypackagers;

import attributes.Attribute;
import attributes.AttributeMap;

import constants.Exceptions;

public interface Packager {

    Package writePackage(Attribute item) throws Exceptions.PackageException;
}
