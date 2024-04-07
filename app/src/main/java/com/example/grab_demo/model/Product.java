package com.example.grab_demo.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private byte[] image;
    private int img;

    public Product(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public Product(String name, byte[] image) {
        this.name = name;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
