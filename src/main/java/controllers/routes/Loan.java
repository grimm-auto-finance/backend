package routes;

import entities.Car;
import entities.CarBuyer;
import entities.LoanData;
import fetchers.LoanDataFetcher;
import constants.Exceptions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import com.sun.net.httpserver.HttpExchange;
import javax.json.*;
import server.Env;
import java.io.BufferedReader;

public class Loan extends controllers.Route {
    @Override
    public String getContext() {
        return "/loan";
    }

    @Override
    protected void post(HttpExchange t) throws IOException {
        // TODO: Parse request body
        // TODO: Create objects from body

        OutputStream os = t.getResponseBody();

        try {
            Car car = new Car("Honda", "Civic", 2002);
            car.setPrice(7000);
            LoanData loanData = LoanDataFetcher.fetch(new CarBuyer(30000.0, 780), car);

            String responseString = loanData.getSensoScore();
            t.sendResponseHeaders(200, responseString.length());
            os.write(responseString.getBytes());
        } catch (Exceptions.CodedException e) {
            String message = e.getMessage();
            if (message != null) {
                t.sendResponseHeaders(e.getCode(), message.length());
                os.write(message.getBytes());
            } else {
                t.sendResponseHeaders(e.getCode(), 0);
            }
        }
        os.close();
    }
}
