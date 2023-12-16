package com.share_ride;

import java.sql.*;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:/Users/wiktorbandzyra/IdeaProjects/share_ride/users.db"; // Update with your database path

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void createRide(String creatorId, String origin, String destination, String date, String time, int seats) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO rides (creator_id, origin, destination, date, time, seats_available) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, creatorId);
            pstmt.setString(2, origin);
            pstmt.setString(3, destination);
            pstmt.setString(4, date);
            pstmt.setString(5, time);
            pstmt.setInt(6, seats);
            pstmt.executeUpdate();
        }
    }

    public static ResultSet getAvailableRides() throws SQLException {
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM rides");
    }

    public static void joinRide(String userId, int rideId) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ride_participants (ride_id, user_id) VALUES (?, ?)")) {
            pstmt.setInt(1, rideId);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
        }
    }
}
