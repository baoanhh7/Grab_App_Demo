package com.example.grab_demo.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int img;
    private String describe;
    private double price;

    public Product(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public Product(String name, String describe, int img) {
        this.name = name;
        this.img = img;
        this.describe = describe;
    }

    public Product(String name, String describe, int img, double price) {
        this.name = name;
        this.img = img;
        this.describe = describe;
        this.price = price;
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
