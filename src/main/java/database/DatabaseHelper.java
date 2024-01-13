package database;

import com.share_ride.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:sqlite:users.db"; // Update with your database path

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
    public static List<String> getRideParticipantNames(int rideId) throws SQLException {
        List<String> participantNames = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT users.display_name FROM ride_participants INNER JOIN users ON ride_participants.user_id = users.id WHERE ride_participants.ride_id = ?")) {
            pstmt.setInt(1, rideId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    participantNames.add(rs.getString("display_name"));
                }
            }
        }
        return participantNames;
    }

    public static void createUser(String username, String password, String displayName, String firstName, String lastName, String email, String phone, String country, String city, String state) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (username, password, display_name, first_name, last_name, email, phone, country, city, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, displayName);
            pstmt.setString(4, firstName);
            pstmt.setString(5, lastName);
            pstmt.setString(6, email);
            pstmt.setString(7, phone);
            pstmt.setString(8, country);
            pstmt.setString(9, city);
            pstmt.setString(10, state);
            pstmt.executeUpdate();
        }
    }
    public static boolean isUserRegisteredForRide(String userId, int rideId) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM ride_participants WHERE user_id = ? AND ride_id = ?")) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, rideId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public static void leaveRide(String userId, int rideId) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ride_participants WHERE user_id = ? AND ride_id = ?")) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, rideId);
            pstmt.executeUpdate();
        }
    }

    public static User getUserById(String userId) throws SQLException {
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("phone"), rs.getString("email"), rs.getString("country"), rs.getString("city"), rs.getString("state"), rs.getString("display_name"));
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
