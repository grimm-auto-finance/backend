package server;

import com.sun.net.httpserver.HttpServer;

import controllers.Route;
import controllers.Routes;

import fetchers.DataBaseFetcher;

import logging.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

class Server {
    public static void main(String[] args) throws IOException, SQLException {
        DataBaseFetcher.connectAndMigrate();
        try {
            DataBaseFetcher.insertPlaceholderData();
        } catch (Exception e) {
            LoggerFactory.getLogger().info("placeholder data may already exist");
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(Env.PORT), 0);
        for (Route route : Routes.routes) {
            server.createContext(route.getContext(), route);
        }
        server.setExecutor(null);
        server.start();
    }
}
