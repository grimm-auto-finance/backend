// layer: interfaceadapters
package packaging;

import attributes.Attribute;

import constants.Exceptions;

public interface Packager {

    Package writePackage(Attribute item) throws Exceptions.PackageException;
}
