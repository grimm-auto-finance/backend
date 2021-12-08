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
        for (int retries = 0; retries < 5; retries++) {
            try {
                DataBaseFetcher.connectAndMigrate();
                break;
            } catch (SQLException e) {
                l.warn("could not connect to database, retrying");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException te) {
                    // this doesn't need to be handled because it'll only be interrupted
                    // if the program is stopped while it's sleeping,
                    // in which case the program is already exiting and we don't
                    // need to report any errors.
                }
            }
        }
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
