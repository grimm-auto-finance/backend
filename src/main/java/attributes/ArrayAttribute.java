package attributes;

import java.util.Arrays;

public class ArrayAttribute extends Attribute {

    /** The array of Attributes stored in this ArrayAttribute */
    private final Attribute[] items;

    /**
     * Constructs a new ArrayAttribute from the given array of Attributes
     *
     * @param items the array of Attributes to be stored in the new ArrayAttribute
     */
    public ArrayAttribute(Attribute[] items) {
        this.items = items;
    }

    /**
     * Returns the array of Attributes stored in this ArrayAttribute
     *
     * @return the Attribute[] stored in this ArrayAttribute
     */
    public Attribute[] getAttribute() {
        return items;
    }

    /**
     * Returns a String representation of this ArrayAttribute Uses Arrays.toString() to generate the
     * String representation of items
     *
     * @return a string representation of this ArrayAttribute
     */
    @Override
    public String toString() {
        return Arrays.toString(items);
    }
}
