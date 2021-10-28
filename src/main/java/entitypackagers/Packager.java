package entitypackagers;

import attributes.AttributeMap;

import constants.Exceptions;

public interface Packager {

    Package writePackage(AttributeMap map) throws Exceptions.PackageException;
}
