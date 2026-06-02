package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GiaoDich implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Date ngayGiaoDich;
    private float tongTien;
    private Ban ban;
    private KhachHang khachHang;
    private User user;
    private List<ChiTietGiaoDich> listChiTietGiaoDich;
    private List<UsedService> listUsedService;

    public GiaoDich() {
        super();
        this.listChiTietGiaoDich = new ArrayList<>();
        this.listUsedService = new ArrayList<>();
    }

    public GiaoDich(int id, Date ngayGiaoDich, float tongTien, Ban ban, KhachHang khachHang, User user,
                    List<ChiTietGiaoDich> listChiTietGiaoDich, List<UsedService> listUsedService) {
        super();
        this.id = id;
        this.ngayGiaoDich = ngayGiaoDich;
        this.tongTien = tongTien;
        this.ban = ban;
        this.khachHang = khachHang;
        this.user = user;
        this.listChiTietGiaoDich = listChiTietGiaoDich;
        this.listUsedService = listUsedService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(Date ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ChiTietGiaoDich> getListChiTietGiaoDich() {
        return listChiTietGiaoDich;
    }

    public void setListChiTietGiaoDich(List<ChiTietGiaoDich> listChiTietGiaoDich) {
        this.listChiTietGiaoDich = listChiTietGiaoDich;
    }

    public List<UsedService> getListUsedService() {
        return listUsedService;
    }

    public void setListUsedService(List<UsedService> listUsedService) {
        this.listUsedService = listUsedService;
    }
}
