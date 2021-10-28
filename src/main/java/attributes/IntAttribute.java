package attributes;

public class IntAttribute extends Attribute {

    // the int item stored in this attribute
    private final int item;

    /**
     * Constructs a new IntAttribute with the given int item
     *
     * @param item
     */
    public IntAttribute(int item) {
        this.item = item;
    }

    /**
     * Returns an Integer representation of the int stored in this IntAttribute
     *
     * @return
     */
    public Integer getAttribute() {
        return item;
    }
}
