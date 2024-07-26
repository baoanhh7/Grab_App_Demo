package com.example.grab_demo.store_owner.model;

import java.sql.Date;
import java.sql.Time;

public class Stores {
    private byte[] hinh;
    private int id;
    private int owner_id;
    private String status;
    private int cate_id;
    private String address;
    private String tensp;
    private Time giomocua;
    private Time giodongcua;
    private Date updated_at;
    private boolean isChecked;

    public Stores(byte[] hinh, int id, int owner_id, String status, int cate_id, String address, String tensp, Time giomocua, Time giodongcua) {
        this.hinh = hinh;
        this.id = id;
        this.owner_id = owner_id;
        this.status = status;
        this.cate_id = cate_id;
        this.address = address;
        this.tensp = tensp;
        this.giomocua = giomocua;
        this.giodongcua = giodongcua;
    }

    public Stores(byte[] hinh, int id, int owner_id, String tensp, Date updated_at) {
        this.hinh = hinh;
        this.id = id;
        this.owner_id = owner_id;
        this.tensp = tensp;
        this.updated_at = updated_at;
    }

    public Stores(String tensp, int owner_id, int id, byte[] hinh) {
        this.tensp = tensp;
        this.owner_id = owner_id;
        this.id = id;
        this.hinh = hinh;
    }

    public Stores(byte[] hinh, String tensp, Time giomocua, Time giodongcua) {
        this.hinh = hinh;
        this.tensp = tensp;
        this.giomocua = giomocua;
        this.giodongcua = giodongcua;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Time getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(Time giomocua) {
        this.giomocua = giomocua;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Time getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(Time giodongcua) {
        this.giodongcua = giodongcua;
    }
}
