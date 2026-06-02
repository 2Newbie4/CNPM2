package model;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String tenKH;
    private String soDT;

    public KhachHang() {
        super();
    }

    public KhachHang(int id, String tenKH, String soDT) {
        super();
        this.id = id;
        this.tenKH = tenKH;
        this.soDT = soDT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }
}
