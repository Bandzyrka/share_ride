package com.share_ride;

public class Session {
    private static Session instance;

    private String userId;
    private String username;
    private String displayName;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUser(String userId, String username, String displayName) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getDisplayName() { return displayName; }

    // You can add more session related properties and methods here
}
