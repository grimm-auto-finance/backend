package attributes;

/** A factory that creates an Attribute given an object that is either an int, Double or String */
public class AttributeFactory {
    /**
     * @param obj The object from which the attribute object is created
     * @return returns an Int, Double or String Attribute, depending on the object in the parameter
     * @throws ClassCastException This is because an incorrect class is used for the parameter
     */
    public static Attribute getAttribute(Object obj) throws ClassCastException {
        if (obj instanceof Integer) {
            return new IntAttribute((Integer) obj);
        } else if (obj instanceof Double) {
            return new DoubleAttribute((Double) obj);
        } else if (obj instanceof String) {
            return new StringAttribute((String) obj);
        } else {
            throw new ClassCastException(
                    "Unable to find appropriate Attribute for Object" + obj.toString());
        }
    }
}
