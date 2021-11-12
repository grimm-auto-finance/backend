package attributes;

public class FakeAttribute extends Attribute {
    private final Object item;

    public FakeAttribute(Object item) {
        this.item = item;
    }

    public Object getAttribute() {
        return this.item;
    }
}
