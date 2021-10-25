package entitypackagers;

public abstract class Package {

    public abstract Object getPackage();

    public String toString() {
        return this.getPackage().toString();
    }
}
