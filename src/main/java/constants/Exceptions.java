package constants;

public class Exceptions {
    public abstract static class CodedException extends Exception {
        public CodedException() {
            super();
        }

        public CodedException(String message) {
            super(message);
        }

        public abstract int getCode();
    }

    public static class FetchException extends CodedException {
        public FetchException() {}

        public FetchException(String message) {
            super(message);
        }

        public int getCode() {
            return 500;
        }
    }

    public static class ParseException extends CodedException {
        public ParseException() {}

        public ParseException(String message) {
            super(message);
        }

        public int getCode() {
            return 400;
        }
    }

    public static class PackageException extends CodedException {
        public PackageException() {}

        public PackageException(String message) {
            super(message);
        }

        public int getCode() {
            return 400;
        }
    }

    public static class MissingMethodException extends CodedException {
        public MissingMethodException() {}

        public MissingMethodException(String message) {
            super(message);
        }

        public int getCode() {
            return 404;
        }
    }
}
