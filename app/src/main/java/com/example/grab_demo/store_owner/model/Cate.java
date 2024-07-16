package com.example.grab_demo.store_owner.model;

public class Cate {
    private String tensp;
    private Integer id;
    private byte[] hinh;

    public Cate(Integer id, String tensp, byte[] hinh) {
        this.id = id;
        this.tensp = tensp;
        this.hinh = hinh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }


    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }
}
