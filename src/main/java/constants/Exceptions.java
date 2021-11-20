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

        public abstract int getCode();
    }

    /** FetchException class that is thrown when a fetch request is made */
    public static class FetchException extends CodedException {
        public FetchException() {}

        /** @param message The exception message */
        public FetchException(String message, Exception e) {
            super(message);
            this.setStackTrace(e.getStackTrace());
        }

        /** @return Returns the specific FetchException code */
        public int getCode() {
            return 500;
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
    }

    public static class FactoryException extends CodedException {
        public int getCode() {
            return 400;
        }

        public FactoryException() {}

        public FactoryException(String message) {
            super(message);
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
