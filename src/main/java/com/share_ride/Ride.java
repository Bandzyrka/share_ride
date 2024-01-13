package com.share_ride;

public class Ride {
    private int id;
    private String origin;
    private String destination;
    private String participants;

    private String date;

    private String time;

    private int seats_available;

    private String creator;

    public Ride() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSeats_available(int seats_available) {
        this.seats_available = seats_available;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getParticipants() {
        return participants;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getSeats_available() {
        return seats_available;
    }

    public String getCreator() {
        return creator;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public void setSeats(int seatsAvailable) {
        this.seats_available = seatsAvailable;
    }
}
