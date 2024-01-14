package controllers;

import com.share_ride.LoginManager;
import com.share_ride.Session;
import com.share_ride.User;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/** Controls the login screen */
public class LoginController {

    @FXML
    TextField user;
    @FXML
    TextField password;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    Session session = Session.getInstance();


    public void initialize() {}

    public void initManager(final LoginManager loginManager) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                String sessionID = authorize();
                if (sessionID != null) {
                    loginManager.authenticated(sessionID);

                }
            }
        });

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginManager.showRegistrationScreen();

            }
        });

    }

    /**
     * Check authorization credentials.
     *
     * If accepted, return a sessionID for the authorized session
     * otherwise, return null.
     */
    String authorize() {
        try {
            JSONObject json = new JSONObject();
            json.put("username", user.getText());
            json.put("password", password.getText());

            URL url = new URL("http://localhost:8000/login");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(json.toString().getBytes(StandardCharsets.UTF_8));

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                Scanner scanner = new Scanner(con.getInputStream()).useDelimiter("\\A");
                String response = scanner.hasNext() ? scanner.next() : "";
                scanner.close();

                JSONObject responseJson = new JSONObject(response);
                User user = new User(responseJson.getInt("id"), responseJson.getString("username"), responseJson.getString("displayName"), responseJson.getString("firstName"), responseJson.getString("lastName"), responseJson.getString("email"), responseJson.getString("phone"), responseJson.getString("country"), responseJson.getString("city"), responseJson.getString("state"), responseJson.getString("password"));
                session.setUser(user);
                return generateSessionID();
            } else {
                // Handle login failure
                showAlert("Login Failed", "Invalid username or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
        return null;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private static int sessionID = 0;

    private String generateSessionID() {
        sessionID++;
        return "session: " + session.getDisplayName();
    }
}