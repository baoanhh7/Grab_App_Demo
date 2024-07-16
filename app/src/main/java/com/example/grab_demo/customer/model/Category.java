package com.example.grab_demo.customer.model;

import java.io.Serializable;

public class Category implements Serializable {
    private int cateID;
    private String name;
    private int img;
    private String describe;
    private double price;
    private byte[] image;

    public Category(int cateID, String name, byte[] image) {
        this.cateID = cateID;
        this.name = name;
        this.image = image;
    }

    public Category(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public Category(String name, String describe, int img) {
        this.name = name;
        this.img = img;
        this.describe = describe;
    }

    public Category(String name, String describe, int img, double price) {
        this.name = name;
        this.img = img;
        this.describe = describe;
        this.price = price;
    }

    public Category(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public int getCateID() {
        return cateID;
    }

    public void setCateID(int cateID) {
        this.cateID = cateID;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
