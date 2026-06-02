package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Ban;
import model.ChiTietGiaoDich;
import model.DichVu;
import model.GiaoDich;
import model.KhachHang;
import model.MonAn;
import model.TableStat;
import model.UsedService;
import model.User;

public class ReportDAO extends DAO {

    public ReportDAO() {
        super();
    }

    public ArrayList<TableStat> getRevenueByTable(Date start, Date end) {
        ArrayList<TableStat> result = new ArrayList<>();
        String sql = "SELECT b.id, b.tenBan, b.kieu, b.khuVuc, "
                   + "COUNT(g.id) AS tongLuotKhach, "
                   + "SUM(g.tongTien) AS doanhThu "
                   + "FROM tblBan b "
                   + "INNER JOIN tblGiaoDich g ON b.id = g.idBan "
                   + "WHERE g.ngayGiaoDich >= ? AND g.ngayGiaoDich <= ? "
                   + "GROUP BY b.id, b.tenBan, b.kieu, b.khuVuc "
                   + "ORDER BY doanhThu DESC, tongLuotKhach DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(start.getTime()));
            ps.setTimestamp(2, new Timestamp(end.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ban ban = new Ban();
                    ban.setId(rs.getInt("id"));
                    ban.setTenBan(rs.getString("tenBan"));
                    ban.setKieu(rs.getString("kieu"));
                    ban.setKhuVuc(rs.getString("khuVuc"));

                    TableStat stat = new TableStat();
                    stat.setBan(ban);
                    stat.setTongLuotKhach(rs.getInt("tongLuotKhach"));
                    stat.setDoanhThu(rs.getFloat("doanhThu"));

                    result.add(stat);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<GiaoDich> getDetailRevenueByTable(int tableId, Date start, Date end) {
        ArrayList<GiaoDich> result = new ArrayList<>();
        String sqlGiaoDich = "SELECT g.id, g.ngayGiaoDich, g.tongTien, "
                           + "kh.id AS kh_id, kh.tenKH, kh.soDT, "
                           + "u.id AS u_id, u.full_name AS tenNV, u.position AS chucVu, "
                           + "b.id AS b_id, b.tenBan, b.kieu, b.khuVuc "
                           + "FROM tblGiaoDich g "
                           + "LEFT JOIN tblKhachHang kh ON g.idKhachHang = kh.id "
                           + "LEFT JOIN users u ON g.idUser = u.id "
                           + "LEFT JOIN tblBan b ON g.idBan = b.id "
                           + "WHERE g.idBan = ? AND g.ngayGiaoDich >= ? AND g.ngayGiaoDich <= ? "
                           + "ORDER BY g.ngayGiaoDich DESC";

        String sqlChiTietGiaoDich = "SELECT ctgd.id, ctgd.soLuong, ctgd.giaBan, "
                                  + "m.id AS m_id, m.name AS tenMon, m.type AS danhMuc, m.price AS donGia "
                                  + "FROM tblChiTietGiaoDich ctgd "
                                  + "LEFT JOIN dishes m ON ctgd.idMonAn = m.id "
                                  + "WHERE ctgd.idGiaoDich = ?";

        String sqlUsedService = "SELECT us.id, us.soLuong, us.thanhTien, "
                              + "d.id AS d_id, d.tenDichVu, d.donGia "
                              + "FROM tblUsedService us "
                              + "LEFT JOIN tblDichVu d ON us.idDichVu = d.id "
                              + "WHERE us.idGiaoDich = ?";

        try (Connection conn = getConnection();
             PreparedStatement psGD = conn.prepareStatement(sqlGiaoDich)) {

            psGD.setInt(1, tableId);
            psGD.setTimestamp(2, new Timestamp(start.getTime()));
            psGD.setTimestamp(3, new Timestamp(end.getTime()));

            try (ResultSet rsGD = psGD.executeQuery()) {
                while (rsGD.next()) {
                    GiaoDich gd = new GiaoDich();
                    gd.setId(rsGD.getInt("id"));
                    gd.setNgayGiaoDich(rsGD.getTimestamp("ngayGiaoDich"));
                    gd.setTongTien(rsGD.getFloat("tongTien"));

                    Ban ban = new Ban();
                    ban.setId(rsGD.getInt("b_id"));
                    ban.setTenBan(rsGD.getString("tenBan"));
                    ban.setKieu(rsGD.getString("kieu"));
                    ban.setKhuVuc(rsGD.getString("khuVuc"));
                    gd.setBan(ban);

                    KhachHang kh = new KhachHang();
                    kh.setId(rsGD.getInt("kh_id"));
                    kh.setTenKH(rsGD.getString("tenKH"));
                    kh.setSoDT(rsGD.getString("soDT"));
                    gd.setKhachHang(kh);

                    User u = new User();
                    u.setId(rsGD.getInt("u_id"));
                    u.setTenNV(rsGD.getString("tenNV"));
                    u.setChucVu(rsGD.getString("chucVu"));
                    gd.setUser(u);

                    List<ChiTietGiaoDich> listCT = getTransactionDishes(conn, gd.getId(), sqlChiTietGiaoDich);
                    gd.setListChiTietGiaoDich(listCT);

                    List<UsedService> listUS = getTransactionServices(conn, gd.getId(), sqlUsedService);
                    gd.setListUsedService(listUS);

                    result.add(gd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<ChiTietGiaoDich> getTransactionDishes(Connection conn, int giaoDichId, String sql) throws Exception {
        List<ChiTietGiaoDich> listCT = new ArrayList<>();
        try (PreparedStatement psCT = conn.prepareStatement(sql)) {
            psCT.setInt(1, giaoDichId);
            try (ResultSet rsCT = psCT.executeQuery()) {
                while (rsCT.next()) {
                    MonAn ma = new MonAn();
                    ma.setId(rsCT.getInt("m_id"));
                    ma.setTenMon(rsCT.getString("tenMon"));
                    ma.setDanhMuc(rsCT.getString("danhMuc"));
                    ma.setDonGia(rsCT.getFloat("donGia"));

                    ChiTietGiaoDich ct = new ChiTietGiaoDich();
                    ct.setId(rsCT.getInt("id"));
                    ct.setSoLuong(rsCT.getInt("soLuong"));
                    ct.setGiaBan(rsCT.getFloat("giaBan"));
                    ct.setMonAn(ma);
                    listCT.add(ct);
                }
            }
        }
        return listCT;
    }

    private List<UsedService> getTransactionServices(Connection conn, int giaoDichId, String sql) throws Exception {
        List<UsedService> listUS = new ArrayList<>();
        try (PreparedStatement psUS = conn.prepareStatement(sql)) {
            psUS.setInt(1, giaoDichId);
            try (ResultSet rsUS = psUS.executeQuery()) {
                while (rsUS.next()) {
                    DichVu dv = new DichVu();
                    dv.setId(rsUS.getInt("d_id"));
                    dv.setTenDichVu(rsUS.getString("tenDichVu"));
                    dv.setDonGia(rsUS.getFloat("donGia"));

                    UsedService us = new UsedService();
                    us.setId(rsUS.getInt("id"));
                    us.setSoLuong(rsUS.getInt("soLuong"));
                    us.setThanhTien(rsUS.getFloat("thanhTien"));
                    us.setService(dv);
                    listUS.add(us);
                }
            }
        }
        return listUS;
    }
}
