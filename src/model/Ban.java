package model;

import java.io.Serializable;

public class Ban implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String tenBan;
    private String kieu;
    private String khuVuc;

    public Ban() {
        super();
    }

    public Ban(int id, String tenBan, String kieu, String khuVuc) {
        super();
        this.id = id;
        this.tenBan = tenBan;
        this.kieu = kieu;
        this.khuVuc = khuVuc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public String getKieu() {
        return kieu;
    }

    public void setKieu(String kieu) {
        this.kieu = kieu;
    }

    public String getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(String khuVuc) {
        this.khuVuc = khuVuc;
    }
}
