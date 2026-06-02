package model;

import java.io.Serializable;

public class DichVu implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String tenDichVu;
    private float donGia;

    public DichVu() {
        super();
    }

    public DichVu(int id, String tenDichVu, float donGia) {
        super();
        this.id = id;
        this.tenDichVu = tenDichVu;
        this.donGia = donGia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }
}
