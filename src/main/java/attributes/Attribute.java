// Layer: ignore
package attributes;

public abstract class Attribute {

    /** Return the item stored in this Attribute. */
    public abstract Object getAttribute();

    /**
     * Returns a string representation of this Attribute.
     *
     * @return Returns the String representation of an Attribute
     */
    public String toString() {
        return getAttribute().toString();
    }
}
