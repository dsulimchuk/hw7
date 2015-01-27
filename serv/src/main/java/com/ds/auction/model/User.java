package com.ds.auction.model;


public class User {
    private int id;
    private String name;
    private String email;
    private boolean getOverbidNotifications;

    public User(String name, String email, boolean getOverbidNotifications) {
        this.name = name;
        this.email = email;
        this.getOverbidNotifications = getOverbidNotifications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGetOverbidNotifications() {
        return getOverbidNotifications;
    }

    public void setGetOverbidNotifications(boolean getOverbidNotifications) {
        this.getOverbidNotifications = getOverbidNotifications;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", getNotif=" + getOverbidNotifications +
                '}';
    }
}
