package com.example.grab_demo.store_owner.model;

public class Message {
    private int sender_id;
    private int receiver_id;
    private String receiver_name;
    private String message;

    public Message(int sender_id, int receiver_id, String receiver_name, String message) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.receiver_name = receiver_name;
        this.message = message;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
