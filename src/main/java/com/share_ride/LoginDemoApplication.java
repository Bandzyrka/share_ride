package com.share_ride;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/** Main application class for the login demo application */
public class LoginDemoApplication extends Application {
    public static void main(String[] args) { launch(args); }
    @Override public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new StackPane());

        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();

        stage.setScene(scene);
        stage.show();
    }

    private void startServer() {
        // Assuming you have a method to start your server
        // This should run on a separate thread to avoid blocking the JavaFX thread
        new Thread(() -> {
            try {
                RideShareServer.main(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}