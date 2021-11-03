package attributes;

public class AttributeFactory {

    public static Attribute createAttribute(Object obj) throws ClassCastException {
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
