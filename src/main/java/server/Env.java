package server;

public class Env {
    public static final String SENSO_API_BASE_URL = System.getenv("SENSO_API_BASE_URL");
    public static final String SENSO_API_KEY = System.getenv("SENSO_API_KEY");
    public static final int PORT = System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;
}
