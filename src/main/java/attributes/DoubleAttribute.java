// layer: ignore
package attributes;

public class DoubleAttribute extends Attribute {

    // the double item stored in this Attribute
    private final double item;

    /**
     * Creates a new DoubleAttribute with the given double item
     *
     * @param item The Double item to be attributized
     */
    public DoubleAttribute(double item) {
        this.item = item;
    }

    /**
     * Returns the double stored in this Attribute
     *
     * @return The Double item stored in the attribute
     */
    public Double getAttribute() {
        return item;
    }
}
