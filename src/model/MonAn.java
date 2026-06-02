package model;

import java.io.Serializable;

public class MonAn implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String tenMon;
    private String danhMuc;
    private float donGia;

    public MonAn() {
        super();
    }

    public MonAn(int id, String tenMon, String danhMuc, float donGia) {
        super();
        this.id = id;
        this.tenMon = tenMon;
        this.danhMuc = danhMuc;
        this.donGia = donGia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }
}
