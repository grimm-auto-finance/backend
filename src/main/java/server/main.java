package server;

import com.sun.net.httpserver.HttpServer;

import fetchers.DataBaseFetcher;

import logging.Logger;
import logging.LoggerFactory;

import routes.Route;
import routes.Routes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

class Server {
    public static void main(String[] args) {
        Logger l = LoggerFactory.getLogger();
        try {
            DataBaseFetcher.connectAndMigrate();
        } catch (SQLException e) {
            l.error("could not connect to database: ", e);
            System.exit(-1);
            return;
        }
        try {
            DataBaseFetcher.insertPlaceholderData();
        } catch (FileNotFoundException e) {
            l.error("placeholder data file not found", e);
            System.exit(-1);
            return;
        }
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(Env.PORT), 0);
        } catch (IOException e) {
            l.error("could not start server", e);
            System.exit(-1);
            return;
        }
        for (Route route : Routes.routes) {
            server.createContext(route.getContext(), route);
        }
        server.setExecutor(null);
        server.start();
    }
}
