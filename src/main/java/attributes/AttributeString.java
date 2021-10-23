package attributes;

public class AttributeString extends Attribute {

    private final String item;

    public AttributeString(String item) {
        this.item = item;
    }

    public String getAttribute() {
        return item;
    }

    public String toString() {
        return item;
    }
}
