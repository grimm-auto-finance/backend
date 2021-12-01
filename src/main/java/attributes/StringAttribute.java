// Layer: ignore
package attributes;

public class StringAttribute extends Attribute {

    // the String item stored in this StringAttribute
    private final String item;

    /**
     * Constructs a new StringAttribute with the given String item
     *
     * @param item The string item to be attributized
     */
    public StringAttribute(String item) {
        this.item = item;
    }

    /**
     * Returns the String item stored in this StringAttribute
     *
     * @return The string item stored in the attribute
     */
    public String getAttribute() {
        return item;
    }
}
