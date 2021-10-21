package server;

import controllers.Routes;
import controllers.Route;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(Env.PORT), 0);
        for (Route route : Routes.routes) {
            server.createContext(route.getContext(), route);
        }
        server.setExecutor(null);
        server.start();
    }
}
