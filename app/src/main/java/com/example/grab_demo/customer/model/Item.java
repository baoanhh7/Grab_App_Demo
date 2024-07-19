package com.example.grab_demo.customer.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int itemId;
    private String itemName;
    private String description;
    private double price;
    private byte[] image;
    private int quantity;
    private String status;
    private int storeId;
    private String createdAt;
    private String updatedAt;

    // Constructor
    public Item(int itemId, String itemName, String description, double price, byte[] image, int quantity, String status, int storeId, String createdAt, String updatedAt) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.status = status;
        this.storeId = storeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Item(int itemId, String itemName, double price, byte[] image) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.image = image;
    }

    public Item(int itemId, String itemName, double price, byte[] image, int quantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
