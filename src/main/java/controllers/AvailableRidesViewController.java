package controllers;

import com.share_ride.Ride;
import com.share_ride.Session;
import com.share_ride.User;
import database.DatabaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

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
            URL url = new URL("http://localhost:8000/availableRides"); // Update with your server URL
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(url.openStream());
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();

                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Ride ride = new Ride();
                    ride.setId(jsonObject.getInt("id"));
                    ride.setOrigin(jsonObject.getString("origin"));
                    ride.setDestination(jsonObject.getString("destination"));
                    ride.setDate(jsonObject.getString("date"));
                    ride.setTime(jsonObject.getString("time"));
                    ride.setSeats(jsonObject.getInt("seats_available"));

                    User creator = DatabaseHelper.getUserById(jsonObject.getInt("creator_id"));
                    ride.setCreator(creator.getDisplayName());
                    List<String> participants = DatabaseHelper.getRideParticipantNames(ride.getId());
                    ride.setParticipants(String.join(", ", participants));

                    ridesTable.getItems().add(ride);
                }
            } else {
                // Handle non-OK response
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private void joinRide(int rideId) {
        sendRideRequest("http://localhost:8000/joinRide", rideId);
    }

    private void leaveRide(int rideId) {
        sendRideRequest("http://localhost:8000/leaveRide", rideId);
    }

    private void sendRideRequest(String urlString, int rideId) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("userId", session.getUserId());
            json.put("rideId", rideId);

            con.getOutputStream().write(json.toString().getBytes(StandardCharsets.UTF_8));

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                updateRide(rideId);
            } else {
                // Handle errors
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private void updateRide(int rideId) throws SQLException {
        Ride rideToUpdate = ridesTable.getItems().stream()
                .filter(ride -> ride.getId() == rideId)
                .findFirst()
                .orElse(null);
        if (rideToUpdate != null) {
            List<String> participants = DatabaseHelper.getRideParticipantNames(rideId);
            rideToUpdate.setParticipants(String.join(", ", participants));
            ridesTable.refresh();
        }
    }
}
