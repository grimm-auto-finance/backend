package server;

import com.sun.net.httpserver.HttpServer;

import controllers.Route;
import controllers.Routes;

import java.io.IOException;
import java.net.InetSocketAddress;

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
