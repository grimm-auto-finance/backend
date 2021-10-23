package controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/** The generic route class that all routes will inherit from */
public abstract class Route implements HttpHandler {
    public abstract String getContext();

    protected void get(HttpExchange t) throws IOException {
        t.sendResponseHeaders(404, 0);
    }

    protected void head(HttpExchange t) throws IOException {
        t.sendResponseHeaders(404, 0);
    }

    protected void post(HttpExchange t) throws IOException {
        t.sendResponseHeaders(404, 0);
    }

    protected void put(HttpExchange t) throws IOException {
        t.sendResponseHeaders(404, 0);
    }

    protected void delete(HttpExchange t) throws IOException {
        t.sendResponseHeaders(404, 0);
    }

    protected void connect(HttpExchange t) throws IOException {
        t.sendResponseHeaders(404, 0);
    }

    protected void options(HttpExchange t) throws IOException {
        t.sendResponseHeaders(404, 0);
    }

    protected void trace(HttpExchange t) throws IOException {
        t.sendResponseHeaders(404, 0);
    }

    protected void patch(HttpExchange t) throws IOException {
        t.sendResponseHeaders(404, 0);
    }

    /**
     * The main handler for all routes, which decides which sub-method to call based on all possible
     * request methods, and closes the request for convenience.
     *
     * @param t The exchange
     */
    public final void handle(HttpExchange t) throws IOException {
        String method = t.getRequestMethod();
        switch (method) {
            case "GET":
                this.get(t);
                break;
            case "HEAD":
                this.head(t);
                break;
            case "POST":
                this.post(t);
                break;
            case "PUT":
                this.put(t);
                break;
            case "DELETE":
                this.delete(t);
                break;
            case "CONNECT":
                this.connect(t);
                break;
            case "OPTIONS":
                this.options(t);
                break;
            case "TRACE":
                this.trace(t);
                break;
            case "PATCH":
                this.patch(t);
                break;
            default:
        }
        t.close();
    }
}
