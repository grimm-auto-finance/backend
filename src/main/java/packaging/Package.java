// layer: interfaceadapters
package packaging;

/** A wrapper class for certain types of Objects that can be made from AttributeMaps */
public abstract class Package {

    /**
     * Get this Package's contents
     *
     * @return the Object stored in this package
     */
    public abstract Object getPackage();

    /**
     * Convert this Package's contents to a String
     *
     * @return a String representation of this Package
     */
    public String toString() {
        return this.getPackage().toString();
    }
}
