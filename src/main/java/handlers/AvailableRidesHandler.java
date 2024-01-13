package handlers;

import database.DatabaseHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONObject;

public class AvailableRidesHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                ResultSet rs = DatabaseHelper.getAvailableRides();
                JSONArray jsonArray = new JSONArray();
                while (rs.next()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", rs.getInt("id"));
                    jsonObject.put("origin", rs.getString("origin"));
                    jsonObject.put("destination", rs.getString("destination"));
                    jsonObject.put("date", rs.getString("date"));
                    jsonObject.put("time", rs.getString("time"));
                    jsonObject.put("seats_available", rs.getInt("seats_available"));
                    jsonArray.put(jsonObject);
                }
                rs.close();

                String response = jsonArray.toString();
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
