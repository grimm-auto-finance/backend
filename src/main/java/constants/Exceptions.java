package constants;

public class Exceptions {
    abstract public static class CodedException extends Exception {
        abstract public int getCode();

        public CodedException() {
            super();
        }

        public CodedException(String message) {
            super(message);
        }
    }

    public static class FetchException extends CodedException {
        public int getCode() { return 500; }

        public FetchException() {}

        public FetchException(String message) {
            super(message);
        }
    }
}
