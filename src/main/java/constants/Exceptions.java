package constants;

public class Exceptions {
    public abstract static class CodedException extends Exception {
        public abstract int getCode();

        public CodedException() {
            super();
        }

        public CodedException(String message) {
            super(message);
        }
    }

    public static class FetchException extends CodedException {
        public int getCode() {
            return 500;
        }

        public FetchException() {}

        public FetchException(String message) {
            super(message);
        }
    }

    public static class ParseException extends CodedException {
        public int getCode() { return 400; }

        public ParseException() {}

        public ParseException(String message) { super(message); }
    }

    public static class PackageException extends CodedException {
        public int getCode() { return 400; }

        public PackageException() {}

        public PackageException(String message) { super(message); }
    }
}
