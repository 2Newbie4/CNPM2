package model;

import java.io.Serializable;

public class UsedService implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int soLuong;
    private float thanhTien;
    private DichVu service;

    public UsedService() {
        super();
    }

    public UsedService(int id, int soLuong, float thanhTien, DichVu service) {
        super();
        this.id = id;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.service = service;
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

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }

    public DichVu getService() {
        return service;
    }

    public void setService(DichVu service) {
        this.service = service;
    }
}
