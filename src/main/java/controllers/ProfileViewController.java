package controllers;

import com.share_ride.LoginManager;
import com.share_ride.Session;
import com.share_ride.User;
import database.DatabaseHelper;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.Scanner;

public class ProfileViewController {
    private LoginManager loginManager;
    private User user;
    DatabaseHelper db = new DatabaseHelper();

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
    private Button editButton;

    public Session session = Session.getInstance();


    @FXML
    public void initialize() {
        setUserData();

    }

    public void setUserData() {
                User userData = session.getUser();
                usernameField.setText(userData.getUsername());
                passwordField.setText(userData.getPassword());
                firstNameField.setText(userData.getFirstName());
                lastNameField.setText(userData.getLastName());
                emailField.setText(userData.getEmail());
                phoneNumberField.setText(userData.getPhone());
                countryField.setText(userData.getCountry());
                cityField.setText(userData.getCity());
                stateField.setText(userData.getState());
    }


    public void handleEdit(MouseEvent mouseEvent) {
        try {
            DatabaseHelper.editUser(session.getUserId(), usernameField.getText(), passwordField.getText(), session.getDisplayName(), firstNameField.getText(),lastNameField.getText(), emailField.getText(), phoneNumberField.getText(), countryField.getText(), cityField.getText(), stateField.getText());
            session.setUser(DatabaseHelper.getUserById(session.getUserId()));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
    }
