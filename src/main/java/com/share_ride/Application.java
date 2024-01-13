package com.share_ride;


import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.RideShareServer;

import java.io.IOException;

/** Main application class */
public class Application extends javafx.application.Application {
    public static void main(String[] args) { launch(args); }
    @Override public void start(Stage stage) throws IOException {

        Scene scene = new Scene(new StackPane());

        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();

        stage.setScene(scene);
        stage.show();
    }

    private void startServer() {
        new Thread(() -> {
            try {
                RideShareServer.main(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}