package com.share_ride;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegistrationFormController {

    private LoginManager loginManager;

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField displayNameField;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText(); // Consider hashing
        String displayName = displayNameField.getText();

        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
            json.put("displayName", displayName);

            URL url = new URL("http://localhost:8000/register");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(json.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }


    @FXML
    private void handleBackToLogin() {
        loginManager.showLoginScreen();
    }
}
