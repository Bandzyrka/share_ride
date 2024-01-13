package controllers;

import com.share_ride.Ride;
import com.share_ride.Session;
import com.share_ride.User;
import database.DatabaseHelper;
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
    @FXML private TableColumn<Ride, String> dateColumn;
    @FXML private TableColumn<Ride, String> timeColumn;
    @FXML private TableColumn<Ride, String> seatsColumn;
    @FXML private TableColumn<Ride, String> creatorColumn;

    @FXML private TableColumn<Ride, Button> joinColumn;
    Session session = Session.getInstance();
    public void initialize() {
        originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        participantsColumn.setCellValueFactory(new PropertyValueFactory<>("participants"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats_available"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
        joinColumn.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button();

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Ride ride = getTableView().getItems().get(getIndex());
                    try {
                        if (DatabaseHelper.isUserRegisteredForRide(session.getUserId(), ride.getId())) {
                            actionButton.setText("Leave");
                            actionButton.setOnAction(event -> leaveRide(ride.getId()));
                        } else {
                            actionButton.setText("Join");
                            actionButton.setOnAction(event -> joinRide(ride.getId()));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Handle exceptions
                    }
                    setGraphic(actionButton);
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
                ride.setDate(rs.getString("date"));
                ride.setTime(rs.getString("time"));
                ride.setSeats(rs.getInt("seats_available"));

                User creator = DatabaseHelper.getUserById(rs.getString("creator_id"));
                ride.setCreator(creator.getDisplayName());
                List<String> participants = DatabaseHelper.getRideParticipantNames(ride.getId());
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
                List<String> participants = DatabaseHelper.getRideParticipantNames(rideId);
                rideToUpdate.setParticipants(String.join(", ", participants));
                ridesTable.refresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
    private void leaveRide(int rideId) {
        try {
            DatabaseHelper.leaveRide(session.getUserId(), rideId);

            // Update the participants list
            Ride rideToUpdate = ridesTable.getItems().stream()
                    .filter(ride -> ride.getId() == rideId)
                    .findFirst()
                    .orElse(null);
            if (rideToUpdate != null) {
                List<String> participants = DatabaseHelper.getRideParticipantNames(rideId);
                rideToUpdate.setParticipants(String.join(", ", participants));
                ridesTable.refresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
}
