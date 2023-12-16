package com.share_ride;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class CreateRideHandler implements HttpHandler {
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

                String creatorId = jsonObject.getString("creatorId");
                String origin = jsonObject.getString("origin");
                String destination = jsonObject.getString("destination");
                String date = jsonObject.getString("date");
                String time = jsonObject.getString("time");
                int seats = jsonObject.getInt("seats");

                DatabaseHelper.createRide(creatorId, origin, destination, date, time, seats);

                String response = "Ride created successfully";
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
