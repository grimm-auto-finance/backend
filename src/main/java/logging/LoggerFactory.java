// Layer: ignore
package logging;

/** A class that allows you to create loggers */
public class LoggerFactory {
    /** @return a new object that implements logger */
    public static Logger getLogger() {
        return new SystemLogger();
    }
}
