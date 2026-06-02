package model;

import java.io.Serializable;

public class ChiTietGiaoDich implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int soLuong;
    private float giaBan;
    private MonAn monAn;

    public ChiTietGiaoDich() {
        super();
    }

    public ChiTietGiaoDich(int id, int soLuong, float giaBan, MonAn monAn) {
        super();
        this.id = id;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.monAn = monAn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(float giaBan) {
        this.giaBan = giaBan;
    }

    public MonAn getMonAn() {
        return monAn;
    }

    public void setMonAn(MonAn monAn) {
        this.monAn = monAn;
    }
}
