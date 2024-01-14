package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.DatabaseHelper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class EditUserHandler implements HttpHandler {
    DatabaseHelper databaseHelper = new DatabaseHelper();
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {
                String line;
                StringBuilder body = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    body.append(line);
                }
                JSONObject jsonObject = new JSONObject(body.toString());
                int userId = jsonObject.getInt("userId");
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                String displayName = jsonObject.getString("displayName");
                String firstName = jsonObject.getString("firstName");
                String lastName = jsonObject.getString("lastName");
                String email = jsonObject.getString("email");
                String phone = jsonObject.getString("phone");
                String country = jsonObject.getString("country");
                String city = jsonObject.getString("city");
                String state = jsonObject.getString("state");
                DatabaseHelper.editUser(userId, username, password, displayName, firstName, lastName, email, phone, country, city, state);
                String response = "Successfully edited user";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
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
