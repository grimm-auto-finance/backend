package entitypackagers;

import attributes.AttributeMap;

public class PackagerFactory {

    public static Packager getPackager(AttributeMap map) {
        return new JsonPackager(map);
    }
}
