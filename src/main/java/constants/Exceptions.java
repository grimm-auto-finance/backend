// Layer: ignore
package constants;

/**
 * A class representing the exceptions that are unique to this project and not prepackaged with Java
 */
public class Exceptions {
    /** Abstract exception parent class which is inherited by other project specific exceptions */
    public abstract static class CodedException extends Exception {
        public CodedException() {
            super();
        }

        public CodedException(String message) {
            super(message);
        }

        public CodedException(String message, Exception e) {
            super(message + ": " + e.getMessage());
            this.setStackTrace(e.getStackTrace());
        }

        public abstract int getCode();
    }

    /** FetchException class that is thrown when a fetch request is made */
    public static class FetchException extends CodedException {
        public FetchException() {}

        /** @param message The exception message */
        public FetchException(String message, Exception e) {
            super(message, e);
        }

        public FetchException(String message) {
            super(message);
        }

        /** @return Returns the specific FetchException code */
        public int getCode() {
            return 500;
        }
    }

    public static class DataBaseException extends CodedException {
        public DataBaseException() {}

        public DataBaseException(String message, Exception e) {
            super(message, e);
        }

        public DataBaseException(String message) {
            super(message);
        }

        public int getCode() {
            return 502;
        }
    }

    /** ParseException class that is thrown whenever a Json object is parsed */
    public static class ParseException extends CodedException {
        public int getCode() {
            return 400;
        }

        public ParseException() {}

        /** @param message The exception message */
        public ParseException(String message) {
            super(message);
        }

        public ParseException(String message, Exception e) {
            super(message, e);
        }
    }

    public static class FactoryException extends CodedException {
        public int getCode() {
            return 400;
        }

        public FactoryException() {}

        public FactoryException(String message) {
            super(message);
        }

        public FactoryException(String message, Exception e) {
            super(message, e);
        }
    }

    /**
     * PackageException class that is thrown whenever a java object is packaged into a Json object
     */
    public static class PackageException extends CodedException {
        public PackageException() {}

        /** @param message The exception message */
        public PackageException(String message) {
            super(message);
        }

        public PackageException(String message, Exception e) {
            super(message, e);
        }

        /** @return Returns the specific PackageException code */
        public int getCode() {
            return 500;
        }
    }
    /**
     * MissingMethodException class that is used only in the routes and thrown whenever a method is
     * missing
     */
    public static class MissingMethodException extends CodedException {
        public MissingMethodException() {}

        /** @param message The exception message */
        public MissingMethodException(String message) {
            super(message);
        }

        /** @return Returns the specific MissingMethodException code */
        public int getCode() {
            return 404;
        }
    }
}
