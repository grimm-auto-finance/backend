// layer: frameworksanddrivers
package server;

import com.sun.net.httpserver.HttpServer;

import constants.Exceptions;

import packaging.JsonPackager;
import packaging.Packager;
import parsing.JsonParser;
import parsing.Parser;
import database.DataBase;
import database.PostgresDataBase;

import logging.Logger;
import logging.LoggerFactory;

import routes.Route;

import java.io.IOException;
import java.net.InetSocketAddress;

class Server {

    public static final DataBase dataBase =
            new PostgresDataBase(
                    "jdbc:postgresql://db:5432/postgres", "postgres", Env.POSTGRES_PASSWORD);
    public static final Logger logger = LoggerFactory.getLogger();
    public static final Packager packager = new JsonPackager();
    public static final Parser parser = new JsonParser();

    public static void main(String[] args) {
        initializeDataBase();
        HttpServer server = initializeServer();
        for (Route route : Routes.routes) {
            server.createContext(route.getContext(), route);
        }
        server.setExecutor(null);
        server.start();
    }

    private static void initializeDataBase() {
        for (int retries = 0; retries < 5; retries++) {
            try {
                dataBase.connectAndMigrate();
                break;
            } catch (Exceptions.DataBaseException e) {
                logger.warn("could not connect to database, retrying");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException te) {
                    // this doesn't need to be handled because it'll only be interrupted
                    // if the program is stopped while it's sleeping,
                    // in which case the program is already exiting, and we don't
                    // need to report any errors.
                }
            }
        }
        try {
            dataBase.connectAndMigrate();
            dataBase.insertPlaceholderData();
        } catch (Exceptions.DataBaseException e) {
            logger.error("could not connect to database", e);
            System.exit(-1);
        }
    }

    private static HttpServer initializeServer() {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(Env.PORT), 0);
        } catch (IOException e) {
            logger.error("could not start server", e);
            System.exit(-1);
            return null;
        }
        return server;
    }
}
