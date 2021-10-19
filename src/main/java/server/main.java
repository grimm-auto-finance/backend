package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

class Server {
    public static void main(String[] args) throws Exception {
        final String PORT = System.getenv("PORT");
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT != null ? Integer.parseInt(PORT) : 8080), 0);
        server.createContext("/", new HelloWorld());
        server.setExecutor(null);
        server.start();
    }

    static class HelloWorld implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Hello World!";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
