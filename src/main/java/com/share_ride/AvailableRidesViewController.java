package com.share_ride;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.util.Scanner;

public class AvailableRidesViewController {

    @FXML private TableView<JSONObject> ridesTable;
    @FXML private TableColumn<JSONObject, String> originColumn;
    @FXML private TableColumn<JSONObject, String> destinationColumn;

    public void initialize() {
        originColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("origin")));
        destinationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getString("destination")));
        fetchRides();
    }

    private void fetchRides() {
        try {
            URL url = new URL("http://localhost:8000/availableRides");
            Scanner sc = new Scanner(url.openStream());
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
            sc.close();

            JSONArray rides = new JSONArray(sb.toString());
            for (int i = 0; i < rides.length(); i++) {
                ridesTable.getItems().add(rides.getJSONObject(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
