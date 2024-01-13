package handlers;

import database.DatabaseHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import java.io.*;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            JSONObject requestData = new JSONObject(query);
            String username = requestData.getString("username");
            String password = requestData.getString("password");
            String displayName = requestData.getString("displayName");
            String firstName = requestData.getString("firstName");
            String lastName = requestData.getString("lastName");
            String email = requestData.getString("email");
            String phone = requestData.getString("phone");
            String country = requestData.getString("country");
            String city = requestData.getString("city");
            String state = requestData.getString("state");
            try {
                DatabaseHelper.createUser(username, password, displayName, firstName, lastName, email, phone, country, city, state);
                String response = "User registered successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                String response = "Error registering user";
                exchange.sendResponseHeaders(400, response.getBytes().length); // Bad Request
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            exchange.getResponseBody().close();
        }
    }
}
