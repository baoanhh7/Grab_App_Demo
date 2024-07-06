package com.example.grab_demo.store_owner.model;

public class DishMenuHSO {
    private byte[] hinh;
    private String tensp;
    private String giasp;
    private boolean isChecked;

    public DishMenuHSO(byte[] hinh, String tensp, String giasp) {
        this.hinh = hinh;
        this.tensp = tensp;
        this.giasp = giasp;
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

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public boolean isChecked() {
        return isChecked;
    }
}
