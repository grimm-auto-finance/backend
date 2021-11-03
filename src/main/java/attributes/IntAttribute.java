package attributes;

public class IntAttribute extends Attribute {

    // the int item stored in this attribute
    private final int item;

    /**
     * Constructs a new IntAttribute with the given int item
     *
     * @param item The int item to be attributized
     */
    public IntAttribute(int item) {
        this.item = item;
    }

    /**
     * Returns an Integer representation of the int stored in this IntAttribute
     *
     * @return The int item stored in the attribute
     */
    public Integer getAttribute() {
        return item;
    }
}
