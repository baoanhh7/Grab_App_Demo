package com.example.grab_demo.store_owner.model;

import java.util.Date;

public class Vouchers {
    private int id;
    private String name;
    private int condition;
    private float discount;
    private Date start_date;
    private Date end_date;
    private int quantity;

    public Vouchers(int id, String name, int condition, float discount, Date start_date, Date end_date, int quantity) {
        this.id = id;
        this.name = name;
        this.condition = condition;
        this.discount = discount;
        this.start_date = start_date;
        this.end_date = end_date;
        this.quantity = quantity;
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

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
