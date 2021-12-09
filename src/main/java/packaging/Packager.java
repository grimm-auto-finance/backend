// layer: interfaceadapters
package packaging;

import attributes.Attribute;

import constants.Exceptions;

/** An interface for classes capable of converting Attributes into Packages */
public interface Packager {

    Package writePackage(Attribute item) throws Exceptions.PackageException;
}
