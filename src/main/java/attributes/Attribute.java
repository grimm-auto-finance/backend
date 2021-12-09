// layer: ignore
package attributes;

/** In essence, Attribute is a wrapper class for Object, but it's more specific as we've only defined certain types of Attributes.
 * This lets us be more certain what types of Objects we're dealing with rather than everything just being labeled as Object.
 */
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
