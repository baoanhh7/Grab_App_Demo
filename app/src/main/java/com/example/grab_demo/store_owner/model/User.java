package com.example.grab_demo.store_owner.model;

public class User {
    private int id;
    private String name;
    private String userType;
    private String status;
    private int VP;

    public User(int id, String name, String status, int VP) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.VP = VP;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVP() {
        return VP;
    }

    public void setVP(int VP) {
        this.VP = VP;
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
}
