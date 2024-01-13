package controllers;

import com.share_ride.LoginManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.w3c.dom.events.MouseEvent;

public class MainViewController {
    @FXML private StackPane contentArea;
    @FXML private Label sessionLabel;
    @FXML private Button logoutButton;

    @FXML private Button createRideButton;
    @FXML private Button availableRidesButton;
    private LoginManager loginManager;

    public void initialize() {
    }

    public void initSessionID(final LoginManager loginManager, String sessionID) {
        this.loginManager = loginManager;
        sessionLabel.setText("Session ID: " + sessionID);
        logoutButton.setOnAction(event -> loginManager.logout());
    }

    @FXML
    private void showCreateRideView() {
        loadView("RideCreate.fxml");
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

    public void showProfileView(ActionEvent actionEvent) {
        loadView("ProfileView.fxml");
    }
}
