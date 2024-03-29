package handlers;

import database.DatabaseHelper;
import com.share_ride.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class JoinRideHandler implements HttpHandler {
    public Session session = Session.getInstance();
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
                int rideId = jsonObject.getInt("rideId");
                int userId = jsonObject.getInt("userId");
                DatabaseHelper.joinRide(userId, rideId);
                String response = "Successfully joined the ride";
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
