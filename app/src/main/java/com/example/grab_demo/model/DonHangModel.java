package com.example.grab_demo.model;

import java.io.Serializable;

public class DonHangModel implements Serializable {
    private String tenDonHang;
    private String maDonHang;
    private String hinhMonAn;
    private String diaChi;

    public DonHangModel(String tenDonHang, String maDonHang, String hinhMonAn) {
        this.tenDonHang = tenDonHang;
        this.maDonHang = maDonHang;
        this.hinhMonAn = hinhMonAn;
        this.diaChi = diaChi;
    }

    public String getTenDonHang() {
        return tenDonHang;
    }

    public void setTenDonHang(String tenDonHang) {
        this.tenDonHang = tenDonHang;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getHinhMonAn() {
        return hinhMonAn;
    }

    public void setHinhMonAn(String hinhMonAn) {
        this.hinhMonAn = hinhMonAn;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}