// Layer: frameworksanddrivers
package server;

import java.net.MalformedURLException;
import java.net.URL;

public class Env {
    private static final String SENSO_API_BASE_URL = System.getenv("SENSO_API_BASE_URL");
    private static final String SENSO_API_KEY = System.getenv("SENSO_API_KEY");
    public static final URL SENSO_SCORE_URL = createScoreURL();
    public static final URL SENSO_RATE_URL = createRateURL();
    public static final int PORT =
            System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;
    public static final String POSTGRES_PASSWORD = System.getenv("POSTGRES_PASSWORD");

    private static URL createScoreURL() throws ExceptionInInitializerError {
        try {
            return new URL(SENSO_API_BASE_URL + "/score?key=" + SENSO_API_KEY);
        } catch (MalformedURLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static URL createRateURL() throws ExceptionInInitializerError {
        try {
            return new URL(SENSO_API_BASE_URL + "/rate?key=" + SENSO_API_KEY);
        } catch (MalformedURLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
