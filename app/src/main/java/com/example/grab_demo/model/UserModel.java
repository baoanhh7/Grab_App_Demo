package com.example.grab_demo.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserModel implements Serializable {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private Integer rating;
    private String userType;
    private String status;
    private String bankAccount;
    private String bankName;
    private String drivingLicense;
    private String licensePlate;
    private byte[] image;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public UserModel() {
    }

    // Parameterized constructor
    public UserModel(int userId, String username, String password, String email, String phoneNumber, String address, Integer rating, String userType, String status, String bankAccount, String bankName, String drivingLicense, String licensePlate, byte[] image, Timestamp createdAt, Timestamp updatedAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.rating = rating;
        this.userType = userType;
        this.status = status;
        this.bankAccount = bankAccount;
        this.bankName = bankName;
        this.drivingLicense = drivingLicense;
        this.licensePlate = licensePlate;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter and setter methods

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
