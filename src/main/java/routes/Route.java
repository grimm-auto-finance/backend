// layer: controllers
package routes;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import constants.Exceptions;
import constants.Exceptions.CodedException;
import constants.Exceptions.MissingMethodException;

import packaging.Packager;
import parsing.Parser;
import logging.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/** The generic route class that all routes will inherit from */
public abstract class Route implements HttpHandler {

    protected final Logger logger;
    protected final Parser parser;
    protected final Packager packager;

    protected Route(Logger logger, Parser parser, Packager packager) {
        this.logger = logger;
        this.parser = parser;
        this.packager = packager;
    }

    /**
     * A method that must be overridden by implementers which sets the URL route that the Route
     * class will handle.
     */
    public abstract String getContext();

    protected void get(HttpExchange t) throws CodedException {
        throw new MissingMethodException();
    }

    protected void head(HttpExchange t) throws CodedException {
        throw new MissingMethodException();
    }

    protected void post(HttpExchange t) throws CodedException {
        throw new MissingMethodException();
    }

    protected void put(HttpExchange t) throws CodedException {
        throw new MissingMethodException();
    }

    protected void delete(HttpExchange t) throws CodedException {
        throw new MissingMethodException();
    }

    protected void connect(HttpExchange t) throws CodedException {
        throw new MissingMethodException();
    }

    protected void options(HttpExchange t) throws CodedException {
        throw new MissingMethodException();
    }

    protected void trace(HttpExchange t) throws CodedException {
        throw new MissingMethodException();
    }

    protected void patch(HttpExchange t) throws CodedException {
        throw new MissingMethodException();
    }

    /**
     * The main handler for all routes, which decides which sub-method to call based on all possible
     * request methods, and closes the request for convenience.
     *
     * @param t the httpexchange that this method must handle
     */
    public final void handle(HttpExchange t) {
        String method = t.getRequestMethod();

        logger.info(
                "received "
                        + method
                        + " request on "
                        + t.getRequestURI()
                        + " from "
                        + t.getRemoteAddress());
        try {
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
                    logger.error("received request with unknown method: " + method);
            }
        } catch (CodedException e) {
            logger.error(e.getMessage() + ":\n" + Arrays.toString(e.getStackTrace()));
            String message;
            if (e instanceof Exceptions.FetchException) {
                message = "Fetch error during request execution";
            } else if (e instanceof Exceptions.ParseException) {
                message = "Parse error during request execution";
            } else if (e instanceof Exceptions.PackageException) {
                message = "Package error during request execution";
            } else if (e instanceof Exceptions.FactoryException) {
                message = "Factory error during request execution";
            } else if (e instanceof Exceptions.DataBaseException) {
                message = "DataBase error during request execution";
            } else {
                message = "Error during request execution";
            }
            respond(t, e.getCode(), message.getBytes());
        }
        t.close();
    }

    public final void respond(HttpExchange t, int code, byte[] body) {
        OutputStream os = t.getResponseBody();
        try {
            t.sendResponseHeaders(code, body.length);
            os.write(body);
        } catch (IOException e) {
            logger.error("while responding to request, encountered: ", e);
        }
    }
}
