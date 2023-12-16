package com.share_ride;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainViewController {
    @FXML private StackPane contentArea;
    @FXML private Label sessionLabel;
    @FXML private Button logoutButton;

    private LoginManager loginManager;

    public void initialize() {}

    public void initSessionID(final LoginManager loginManager, String sessionID) {
        this.loginManager = loginManager;
        sessionLabel.setText("Session ID: " + sessionID);
        logoutButton.setOnAction(event -> loginManager.logout());
    }

    @FXML
    private void showCreateRideView() {
        loadView("ride-create.fxml");
    }

    @FXML
    private void showAvailableRidesView() {
        loadView("AvailableRidesView.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception, e.g., show an alert.
        }
    }
}
