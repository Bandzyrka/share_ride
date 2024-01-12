package com.share_ride;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AvailableRidesViewController {

    @FXML private TableView<Ride> ridesTable;
    @FXML private TableColumn<Ride, String> originColumn;
    @FXML private TableColumn<Ride, String> destinationColumn;
    @FXML private TableColumn<Ride, String> participantsColumn;
    @FXML private TableColumn<Ride, Button> joinColumn;
    Session session = Session.getInstance();
    public void initialize() {
        originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        participantsColumn.setCellValueFactory(new PropertyValueFactory<>("participants"));
        joinColumn.setCellFactory(col -> new TableCell<>() {
            private final Button joinButton = new Button("Join");

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(joinButton);
                    joinButton.setOnAction(event -> joinRide(getTableView().getItems().get(getIndex()).getId()));
                }
            }
        });

        fetchRides();
    }

    private void fetchRides() {
        try {
            ResultSet rs = DatabaseHelper.getAvailableRides();
            while (rs.next()) {
                Ride ride = new Ride();
                ride.setId(rs.getInt("id"));
                ride.setOrigin(rs.getString("origin"));
                ride.setDestination(rs.getString("destination"));
                List<String> participants = DatabaseHelper.getRideParticipants(ride.getId());
                ride.setParticipants(String.join(", ", participants));
                ridesTable.getItems().add(ride);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private void joinRide(int rideId) {
        try {
            DatabaseHelper.joinRide(session.getUserId(), rideId);

            // Update the participants list
            Ride rideToUpdate = ridesTable.getItems().stream()
                    .filter(ride -> ride.getId() == rideId)
                    .findFirst()
                    .orElse(null);
            if (rideToUpdate != null) {
                List<String> participants = DatabaseHelper.getRideParticipants(rideId);
                rideToUpdate.setParticipants(String.join(", ", participants));
                ridesTable.refresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
}
