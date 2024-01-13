package controllers;

import com.share_ride.LoginManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class RegistrationFormController {

    private LoginManager loginManager;


    public void setLoginManager(LoginManager loginManager) {
        this.loginManager = loginManager;
    }
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;

    @FXML private TextField countryField;
    @FXML private TextField cityField;
    @FXML private TextField stateField;

    @FXML private Button registerButton;

    @FXML public void initialize() {
        registerButton.setDisable(true);
        addTextFieldsListeners();

    }

    private void addTextFieldsListeners() {
        TextField[] textFields = {usernameField, passwordField, firstNameField, lastNameField, emailField, phoneNumberField, countryField, cityField, stateField};
        for (TextField textField : textFields) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                checkFieldsAndSetButtonState();
            });
        }
    }

    private void checkFieldsAndSetButtonState() {
        boolean allFieldsFilled = firstNameField.getText().trim().isEmpty() ||
                lastNameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                phoneNumberField.getText().trim().isEmpty() ||
                usernameField.getText().trim().isEmpty() ||
                countryField.getText().trim().isEmpty() ||
                stateField.getText().trim().isEmpty() ||
                cityField.getText().trim().isEmpty() ||
                passwordField.getText().trim().isEmpty();

        registerButton.setDisable(allFieldsFilled);
    }

    @FXML
    private void handleRegister() throws SQLException, IOException {
        String username = usernameField.getText();
        String password = passwordField.getText(); // Consider hashing
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneNumberField.getText();
        String country = countryField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        String displayName = firstName + "_" + lastName;

        try {
            URL url = new URL("http://localhost:8000/register");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            String jsonInputString = new JSONObject()
                    .put("username", username)
                    .put("password", password)
                    .put("displayName", displayName)
                    .put("firstName", firstName)
                    .put("lastName", lastName)
                    .put("email", email)
                    .put("phone", phone)
                    .put("country", country)
                    .put("city", city)
                    .put("state", state)
                    .toString();
            try (java.io.OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                loginManager.showLoginScreen();
            } else {
                // Handle error
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
}
