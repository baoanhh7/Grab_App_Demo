package com.example.grab_demo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderItem implements Serializable {
    private int itemId;
    private String itemName;
    private String description;
    private BigDecimal price;
    private byte[] image;
    private int quantity;
    private String status;
    private int storeId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public OrderItem() {
    }

    // Parameterized constructor
    public OrderItem(int itemId, String itemName, String description, BigDecimal price, byte[] image, int quantity, String status, int storeId, Timestamp createdAt, Timestamp updatedAt) {
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

    // Getter and setter methods

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
