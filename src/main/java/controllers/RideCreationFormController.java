package controllers;

import com.share_ride.Session;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class    RideCreationFormController {

    public Session session = Session.getInstance();

    @FXML private TextField originField;
    @FXML private TextField destinationField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField;
    @FXML private TextField seatsField;

    @FXML
    private void handleSubmit() {
        String origin = originField.getText();
        String destination = destinationField.getText();
        String date = datePicker.getValue().toString();
        String time = timeField.getText();
        int seats = Integer.parseInt(seatsField.getText());

        JSONObject json = new JSONObject();
        json.put("creatorId", session.getUserId());
        json.put("origin", origin);
        json.put("destination", destination);
        json.put("date", date);
        json.put("time", time);
        json.put("seats", seats);

        try {
            URL url = new URL("http://localhost:8000/createRide");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(json.toString().getBytes(StandardCharsets.UTF_8));
            con.getInputStream(); // To trigger the request
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions (show alert to user, etc.)
        }
    }
}
