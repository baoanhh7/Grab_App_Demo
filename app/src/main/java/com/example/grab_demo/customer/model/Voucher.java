package com.example.grab_demo.customer.model;

import java.io.Serializable;
import java.util.Date;

public class Voucher implements Serializable {
    private int voucherId;
    private String voucherName;
    private int condition;
    private double discount;
    private Date startDate;
    private Date endDate;
    private int quantity;
    private Date createdAt;
    private Date updatedAt;

    public Voucher(int voucherId, String voucherName, int condition, double discount, Date startDate, Date endDate, int quantity, Date createdAt, Date updatedAt) {
        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.condition = condition;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Voucher(int voucherId, String voucherName, double discount, Date startDate, int quantity) {
        this.voucherId = voucherId;
        this.voucherName = voucherName;
        this.discount = discount;
        this.startDate = startDate;
        this.quantity = quantity;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
