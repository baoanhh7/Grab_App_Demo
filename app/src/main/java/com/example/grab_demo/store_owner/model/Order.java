package com.example.grab_demo.store_owner.model;

public class Order {
    private int id;
    private int deliverId;
    private String status;

    public Order(int id, int deliverId, String status) {
        this.id = id;
        this.deliverId = deliverId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(int deliverId) {
        this.deliverId = deliverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
