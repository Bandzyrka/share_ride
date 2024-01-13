package controllers;

import com.share_ride.LoginManager;
import com.share_ride.Session;
import com.share_ride.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Scanner;

public class ProfileViewController {
    private LoginManager loginManager;
    private User user;

    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField countryField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField stateField;

    @FXML
    private Button registerButton;

    @FXML
    public void initialize() {
        registerButton.setDisable(true);
        fetchUserData(Session.getInstance().getUserId());

    }

    public void fetchUserData(String userId) {
        try {
            URL url = new URL("http://localhost:8000/getUserById?id=" + userId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(url.openStream());
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();

                JSONObject userData = new JSONObject(response);
                usernameField.setText(userData.getString("username"));
                passwordField.setText(userData.getString("password"));
                firstNameField.setText(userData.getString("firstName"));
                lastNameField.setText(userData.getString("lastName"));
                emailField.setText(userData.getString("email"));
                phoneNumberField.setText(userData.getString("phoneNumber"));
                countryField.setText(userData.getString("country"));
                cityField.setText(userData.getString("city"));
                stateField.setText(userData.getString("state"));
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @FXML
    private void handleBackToLogin() {
        loginManager.showLoginScreen();
    }

    public void handleEdit(MouseEvent mouseEvent) {
    }
}