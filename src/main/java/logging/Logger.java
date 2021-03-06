// layer: ignore
package logging;

/** An interface that accepts and communicates messages various levels. */
public interface Logger {
    /**
     * Display the given message as info, the lowest level of log severity.
     *
     * @param string the message, should not begin with a capital or end with a period
     */
    void info(String string);

    /**
     * Display the given message as a warning, the middle level of log severity.
     *
     * @param string the message, should not begin with a capital or end with a period
     */
    void warn(String string);

    /**
     * Display the given message as an error, the highest level of log severity.
     *
     * @param string the message, should not begin with a capital or end with a period
     */
    void error(String string);

    /**
     * Display the given message as an error, the highest level of log severity, along with the
     * stacktrace of the exception argument.
     *
     * @param string the message, should not begin with a capital or end with a period
     * @param e the exception whose stacktrace will be used
     */
    void error(String string, Exception e);
}
