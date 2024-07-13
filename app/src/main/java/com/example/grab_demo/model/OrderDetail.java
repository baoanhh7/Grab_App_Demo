package com.example.grab_demo.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetail implements Serializable {
    private int orderDetailId;
    private int orderId;
    private int itemId;
    private int quantity;
    private BigDecimal price;
    private String description; // Thêm description vào OrderDetail
    private int storeId;

    // Default constructor
    public OrderDetail() {
    }

    // Parameterized constructor
    public OrderDetail(int orderDetailId, int orderId, int itemId, int quantity, BigDecimal price, String description) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    // Getter and setter methods

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailId=" + orderDetailId +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", storeId=" + storeId +
                '}';
    }
}
