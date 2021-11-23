package logging;

/**
 * An implementation of Logger that prints info and warning logs to stdout, and errors to stderr.
 */
class SystemLogger implements Logger {
    @Override
    public void info(String string) {
        System.out.println("[ \u001B[36mINFO\u001B[0m ] " + string);
    }

    @Override
    public void warn(String string) {
        System.out.println("[ \u001B[33mWARN\u001B[0m ] " + string);
    }

    @Override
    public void error(String string) {
        System.err.println("[ \u001B[31mERROR\u001B[0m ] " + string);
    }

    @Override
    public void error(String string, Exception e) {
        error(string + ": " + e.getMessage());
        e.printStackTrace();
    }
}
