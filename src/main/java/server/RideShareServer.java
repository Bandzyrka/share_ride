package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;
import handlers.*;

public class RideShareServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/createRide", new CreateRideHandler());
        server.createContext("/availableRides", new AvailableRidesHandler());
        server.createContext("/getUserById", new GetUserByIdHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/joinRide", new JoinRideHandler());
        server.createContext("/leaveRide", new LeaveRideHandler());
        server.createContext("/editUser", new EditUserHandler());
        server.setExecutor(Executors.newFixedThreadPool(10)); // creates a default executor
        server.start();
        System.out.println("Server started on port 8000");
    }
}
