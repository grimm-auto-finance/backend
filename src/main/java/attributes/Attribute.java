package attributes;

public abstract class Attribute {

    abstract Object getAttribute();

    public String toString() {
        return getAttribute().toString();
    }

}
