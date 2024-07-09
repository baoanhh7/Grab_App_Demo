package com.example.grab_demo.model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private String itemName;
    private int quantity;
    private int price;

    public OrderItem(String itemName, int quantity, int price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
