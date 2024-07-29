package com.example.grab_demo.customer.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private int orderId;
    private int customerId;
    private int storeId;
    private Integer deliveryId;
    private double deliveryPrice;
    private double totalPrice;
    private String paymentMethod;
    private String status;
    private int voucherId;
    private Date createdAt;
    private Date updatedAt;

    // Constructor
    public Order(int orderId, int customerId, int storeId, Integer deliveryId, double deliveryPrice,
                 double totalPrice, String paymentMethod, String status, int voucherId,
                 Date createdAt, Date updatedAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.storeId = storeId;
        this.deliveryId = deliveryId;
        this.deliveryPrice = deliveryPrice;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order(int orderId, int customerId, double totalPrice, String status, int voucherId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.voucherId = voucherId;
    }

    public Order(int orderId, Integer deliveryId, double totalPrice, String status) {
        this.orderId = orderId;
        this.deliveryId = deliveryId;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order(int orderId, Integer deliveryId, double totalPrice, String status, int voucherId) {
        this.orderId = orderId;
        this.deliveryId = deliveryId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.voucherId = voucherId;
    }

    // Getters and setters

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
