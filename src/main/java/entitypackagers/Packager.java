package entitypackagers;

import attributes.AttributeMap;

public interface Packager {

    Package writePackage(AttributeMap map) throws Exception;
}
