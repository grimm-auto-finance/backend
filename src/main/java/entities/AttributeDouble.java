package entities;

public class AttributeDouble extends Attribute {

    private double item;

    public AttributeDouble(double item) {
        this.item = item;
    }

    public Double getAttribute() {
        return item;
    }

    public String toString() {
        return Double.toString(item);
    }
}
