package com.example.grab_demo.store_owner.model;

public class Stores {
    private byte[] hinh;
    private String tensp;
    private String giomocua;
    private boolean isChecked;

    public Stores(byte[] hinh, String tensp, String giomocua) {
        this.hinh = hinh;
        this.tensp = tensp;
        this.giomocua = giomocua;
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

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
