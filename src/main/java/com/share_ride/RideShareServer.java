package com.share_ride;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import com.sun.net.httpserver.HttpServer;

public class RideShareServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/createRide", new CreateRideHandler());
        server.createContext("/availableRides", new AvailableRidesHandler());
        server.createContext("/joinRide", new JoinRideHandler());
        server.setExecutor(Executors.newFixedThreadPool(10)); // creates a default executor
        server.start();
        System.out.println("Server started on port 8000");
    }
}
