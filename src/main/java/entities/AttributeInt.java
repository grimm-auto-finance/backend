package entities;

public class AttributeInt extends Attribute {

    private final int item;

    public AttributeInt(int item) {
        this.item = item;
    }

    public Integer getAttribute() {
        return item;
    }
}
