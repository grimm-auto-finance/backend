package attributes;

public class AttributeFactory {

    /**
     * Constructs and returns the Attribute corresponding to the type of obj
     *
     * @param obj the object to be stored in the newly created Attribute
     * @return an Attribute that stores obj
     * @throws ClassCastException if the type of obj does not correspond to any known Attribute
     *     types
     */
    public static Attribute createAttribute(Object obj) throws ClassCastException {
        if (obj instanceof Integer) {
            return new IntAttribute((Integer) obj);
        } else if (obj instanceof Double) {
            return new DoubleAttribute((Double) obj);
        } else if (obj instanceof String) {
            return new StringAttribute((String) obj);
        } else if (obj instanceof Attribute[]) {
            return new ArrayAttribute((Attribute[]) obj);
        } else {
            throw new ClassCastException(
                    "Unable to find appropriate Attribute for Object " + obj.toString());
        }
    }
}
