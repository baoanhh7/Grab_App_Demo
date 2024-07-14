package com.example.grab_demo.store_owner.model;

public class DishMenuHSO {
    private byte[] hinh;
    private int id;
    private String tensp;
    private String mota;
    private Double giasp;
    private int soluong;
    private int colors;
    private boolean isChecked;

    public DishMenuHSO(String tensp, int colors) {
        this.tensp = tensp;
        this.colors = colors;
    }

    public DishMenuHSO(byte[] hinh, String tensp, Double giasp, int soluong) {
        this.hinh = hinh;
        this.tensp = tensp;
        this.giasp = giasp;
        this.soluong = soluong;
    }

    public DishMenuHSO(byte[] hinh, int id, String tensp, String mota, Double giasp, int soluong) {
        this.hinh = hinh;
        this.id = id;
        this.tensp = tensp;
        this.mota = mota;
        this.giasp = giasp;
        this.soluong = soluong;
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
