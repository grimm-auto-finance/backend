// layer: ignore
package constants;

/**
 * A class representing the exceptions that are unique to this project and not prepackaged with Java
 */
public class Exceptions {

    /** An Exception associated with a specific Code These codes are based on HTTP Error Codes. */
    public abstract static class CodedException extends Exception {
        /** Construct a new CodedException */
        public CodedException() {
            super();
        }

        /** Construct a new CodedException with a specific message */
        public CodedException(String message) {
            super(message);
        }

        /**
         * Construct a new CodedException with a specific message as well as incorporating the
         * information of another Exception
         *
         * @param message the message for this CodedException
         * @param e an Exception whose information should be incorporated into this CodedException,
         *     such as message and stack trace
         */
        public CodedException(String message, Exception e) {
            super(message + ": " + e.getMessage());
            this.setStackTrace(e.getStackTrace());
        }

        /** Return the error code for this CodedException */
        public abstract int getCode();
    }

    /** A CodedException that might be thrown during Fetching */
    public static class FetchException extends CodedException {

        /**
         * Constructs a new FetchException with the given message and Exception
         *
         * @param message The exception message
         * @param e an Exception whose information should be incorporated into this FetchException
         */
        public FetchException(String message, Exception e) {
            super(message, e);
        }

        /** Constructs a new FetchException with the given message */
        public FetchException(String message) {
            super(message);
        }

        /** Return the error code for this FetchException: 500 */
        public int getCode() {
            return 500;
        }
    }

    /** A CodedException that might be thrown during database access */
    public static class DataBaseException extends CodedException {

        /**
         * Constructs a new DataBaseException with the given message and Exception
         *
         * @param message the exception message
         * @param e an Exception whose information should be incorporated into this
         *     DataBaseException
         */
        public DataBaseException(String message, Exception e) {
            super(message, e);
        }

        /** Constructs a new DataBaseException with the given message */
        public DataBaseException(String message) {
            super(message);
        }

        /** Return the error code for this DataBaseException: 502 */
        public int getCode() {
            return 502;
        }
    }

    /** A CodedException that might be thrown during Parsing of input data */
    public static class ParseException extends CodedException {

        /** Return the error code for this ParseException: 400 */
        public int getCode() {
            return 400;
        }

        /** Constructs a new ParseException with the given message */
        public ParseException(String message) {
            super(message);
        }

        /**
         * Constructs a new ParseException with the given message and Exception
         *
         * @param message the exception message
         * @param e an Exception whose information should be incorporated into this ParseException
         */
        public ParseException(String message, Exception e) {
            super(message, e);
        }
    }

    /** A CodedException that might be thrown during Factory creation of an object */
    public static class FactoryException extends CodedException {

        /** Return the error code for this FactoryException: 400 */
        public int getCode() {
            return 400;
        }

        public FactoryException() {}

        /** Constructs a new FactoryException with the given message */
        public FactoryException(String message) {
            super(message);
        }

        /**
         * Constructs a new FactoryException with the given message and Exception
         *
         * @param message the exception message
         * @param e an Exception whose information should be incorporated into this FactoryException
         */
        public FactoryException(String message, Exception e) {
            super(message, e);
        }
    }

    /** A CodedException that might be thrown during Packaging of an Entity */
    public static class PackageException extends CodedException {
        public PackageException() {}

        /** Constructs a new PackageException with the given message */
        public PackageException(String message) {
            super(message);
        }

        /**
         * Constructs a new PackageException with the given message and Exception
         *
         * @param message the exception message
         * @param e an Exception whose information should be incorporated into this PackageException
         */
        public PackageException(String message, Exception e) {
            super(message, e);
        }

        /** Return the error code for this PackageException: 500 */
        public int getCode() {
            return 500;
        }
    }
    /**
     * A CodedException that will be thrown when attempting to call an unimplemented method. This
     * Exception is only ever thrown when we receive an improper HTTP request type (anything besides
     * POST).
     */
    public static class MissingMethodException extends CodedException {
        public MissingMethodException() {}

        /** Return the error code for this MissingmethodException: 404 */
        public int getCode() {
            return 404;
        }
    }
}
