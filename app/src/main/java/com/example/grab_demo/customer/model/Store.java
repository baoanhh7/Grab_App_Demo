package com.example.grab_demo.customer.model;

import java.io.Serializable;

public class Store implements Serializable {
    private int storeId;
    private String storeName;
    private int ownerId;
    private String status;
    private int cateId;
    private String openStore;
    private byte[] image;
    private String createdAt;
    private String updatedAt;
    private String address;

    public Store(int storeId, String storeName, int ownerId, String status, int cateId, String openStore, byte[] image, String createdAt, String updatedAt, String address) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.ownerId = ownerId;
        this.status = status;
        this.cateId = cateId;
        this.openStore = openStore;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.address = address;
    }

    public Store(int storeId, String storeName, int cateId, byte[] image, String status, String address) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.cateId = cateId;
        this.image = image;
        this.status = status;
        this.address = address;
    }


    // Getters and Setters
    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getOpenStore() {
        return openStore;
    }

    public void setOpenStore(String openStore) {
        this.openStore = openStore;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
