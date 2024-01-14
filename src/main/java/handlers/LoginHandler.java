package handlers;

import com.share_ride.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.DatabaseHelper;
import org.json.JSONObject;
import java.io.*;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();

            JSONObject requestData = new JSONObject(query);
            String username = requestData.getString("username");
            String password = requestData.getString("password");

            try {
                User user = DatabaseHelper.authenticateUser(username, password);
                if (user != null) {
                    JSONObject jsonResponse = new JSONObject(user);
                    String response = jsonResponse.toString();
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                } else {
                    exchange.sendResponseHeaders(401, -1); // Unauthorized
                }
                exchange.getResponseBody().close();
            } catch (Exception e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, 0); // Internal Server Error
                exchange.getResponseBody().close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            exchange.getResponseBody().close();
        }
    }
}
