package attributes;

public class ArrayAttribute extends Attribute {

    private final Attribute[] items;

    public ArrayAttribute(Attribute[] items) {
        this.items = items;
    }

    public Attribute[] getAttribute() {
        return items;
    }
}
