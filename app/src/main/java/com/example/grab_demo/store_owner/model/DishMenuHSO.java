package com.example.grab_demo.store_owner.model;

public class DishMenuHSO {
    private byte[] hinh;
    private String tensp;
    private  String mota;
    private Double giasp;
    private  int soluong;
    private boolean isChecked;

    public DishMenuHSO(byte[] hinh, String tensp, String mota, Double giasp, int soluong) {
        this.hinh = hinh;
        this.tensp = tensp;
        this.mota = mota;
        this.giasp = giasp;
        this.soluong = soluong;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
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

    public Double getGiasp() {
        return giasp;
    }

    public void setGiasp(Double giasp) {
        this.giasp = giasp;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
