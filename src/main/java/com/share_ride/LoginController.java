package com.share_ride;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/wiktorbandzyra/IdeaProjects/share_ride/users.db");

            // Prepare a statement to execute SQL query
            String query = "SELECT * FROM users WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getText());

            // Execute the query
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String retrievedPassword = resultSet.getString("password");
                String retrievedUserId = resultSet.getString("id");
                String username = resultSet.getString("username");
                String displayName = resultSet.getString("display_name");
                if (retrievedPassword.equals(password.getText())) {
                    session.setUser(retrievedUserId, username, displayName);
                    return generateSessionID();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any errors that might have occurred
        } finally {
            // Close all opened resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static int sessionID = 0;

    private String generateSessionID() {
        sessionID++;
        System.out.println(session.getUserId());
        return "session: " + session.getDisplayName();
    }
}