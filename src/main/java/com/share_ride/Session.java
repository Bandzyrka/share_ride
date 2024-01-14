package com.share_ride;

public class Session {
    public static Session instance;

    public User user;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Getters
    public int getUserId() { return user.getId(); }
    public String getUsername() { return user.getUsername(); }
    public String getDisplayName() { return user.getDisplayName(); }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
