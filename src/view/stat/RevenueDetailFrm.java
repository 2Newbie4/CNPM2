package view.stat;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.ReportDAO;
import model.ChiTietGiaoDich;
import model.GiaoDich;
import model.TableStat;
import model.UsedService;
import model.User;

public class RevenueDetailFrm extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private ArrayList<GiaoDich> listGiaoDich;
    private TableStat selectedStat;
    private Date startDate, endDate;
    private JTable tblGiaoDich;
    private JTextArea txtItemsDetail;
    private JButton btnBack;
    private User user;

    public RevenueDetailFrm(User user, ArrayList<GiaoDich> listGiaoDich, TableStat selectedStat, Date startDate, Date endDate) {
        super("Chi tiết giao dịch của bàn");
        this.user = user;
        this.listGiaoDich = listGiaoDich;
        this.selectedStat = selectedStat;
        this.startDate = startDate;
        this.endDate = endDate;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        JLabel lblTitle = new JLabel("CHI TIẾT GIAO DỊCH - " + selectedStat.getBan().getTenBan());
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(18.0f));
        pnMain.add(lblTitle);

        JLabel lblInfo = new JLabel("Kiểu: " + selectedStat.getBan().getKieu() + " | Khu vực: " + selectedStat.getBan().getKhuVuc());
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnMain.add(lblInfo);

        JLabel lblTime = new JLabel("Khoảng thời gian: " + sdf.format(startDate) + " - " + sdf.format(endDate));
        lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnMain.add(lblTime);

        JLabel lblSummary = new JLabel("Tổng lượt khách: " + selectedStat.getTongLuotKhach() + " | Tổng doanh thu: " + String.format("%,.0f", selectedStat.getDoanhThu()) + " VND");
        lblSummary.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSummary.setFont(lblSummary.getFont().deriveFont(14.0f));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));
        pnMain.add(lblSummary);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        // Transactions Table
        String[] columns = {"STT", "Mã Giao Dịch", "Ngày Giao Dịch", "Khách Hàng", "Nhân Viên Phục Vụ", "Tổng Tiền (VND)"};
        Object[][] data = new Object[listGiaoDich.size()][6];
        SimpleDateFormat sdfTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (int i = 0; i < listGiaoDich.size(); i++) {
            GiaoDich gd = listGiaoDich.get(i);
            data[i][0] = (i + 1);
            data[i][1] = gd.getId();
            data[i][2] = sdfTime.format(gd.getNgayGiaoDich());
            data[i][3] = (gd.getKhachHang() != null) ? gd.getKhachHang().getTenKH() : "Vãng lai";
            data[i][4] = (gd.getUser() != null) ? gd.getUser().getTenNV() : "N/A";
            data[i][5] = String.format("%,.0f", gd.getTongTien());
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblGiaoDich = new JTable(tableModel);
        tblGiaoDich.setFillsViewportHeight(true);
        JScrollPane scrollGiaoDich = new JScrollPane(tblGiaoDich);
        scrollGiaoDich.setPreferredSize(new Dimension(650, 150));
        pnMain.add(scrollGiaoDich);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        // Items Details Text Area (Populated when row is selected)
        JLabel lblDetails = new JLabel("Chi tiết Món ăn & Dịch vụ của giao dịch đã chọn:");
        lblDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnMain.add(lblDetails);

        txtItemsDetail = new JTextArea(6, 50);
        txtItemsDetail.setEditable(false);
        txtItemsDetail.setText("Nhấp vào một dòng giao dịch ở trên để xem chi tiết món ăn và dịch vụ đã gọi.");
        JScrollPane scrollDetail = new JScrollPane(txtItemsDetail);
        pnMain.add(scrollDetail);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        tblGiaoDich.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = tblGiaoDich.getSelectedRow();
                    if (row >= 0 && row < listGiaoDich.size()) {
                        showTransactionItems(listGiaoDich.get(row));
                    }
                }
            }
        });

        JPanel pnBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnBack = new JButton("Quay lại");
        btnBack.addActionListener(this);
        pnBtn.add(btnBack);
        pnMain.add(pnBtn);

        this.setContentPane(pnMain);
        this.setSize(700, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void showTransactionItems(GiaoDich gd) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Chi tiết Giao dịch ID: %d\n", gd.getId()));
        sb.append("----------------------------------------------------------------------\n");
        sb.append("DANH SÁCH MÓN ĂN:\n");
        if (gd.getListChiTietGiaoDich().isEmpty()) {
            sb.append("  (Không gọi món ăn)\n");
        } else {
            sb.append(String.format("  %-25s %-10s %-15s %-15s\n", "Tên món", "Số lượng", "Đơn giá", "Thành tiền"));
            for (ChiTietGiaoDich ct : gd.getListChiTietGiaoDich()) {
                float total = ct.getSoLuong() * ct.getGiaBan();
                sb.append(String.format("  %-25s %-10d %-15s %-15s\n",
                        ct.getMonAn().getTenMon(),
                        ct.getSoLuong(),
                        String.format("%,.0f", ct.getGiaBan()),
                        String.format("%,.0f", total)));
            }
        }
        sb.append("\nDANH SÁCH DỊCH VỤ:\n");
        if (gd.getListUsedService().isEmpty()) {
            sb.append("  (Không dùng dịch vụ)\n");
        } else {
            sb.append(String.format("  %-25s %-10s %-15s %-15s\n", "Tên dịch vụ", "Số lượng", "Đơn giá", "Thành tiền"));
            for (UsedService us : gd.getListUsedService()) {
                float unitPrice = us.getService().getDonGia();
                sb.append(String.format("  %-25s %-10d %-15s %-15s\n",
                        us.getService().getTenDichVu(),
                        us.getSoLuong(),
                        String.format("%,.0f", unitPrice),
                        String.format("%,.0f", us.getThanhTien())));
            }
        }
        sb.append("----------------------------------------------------------------------\n");
        sb.append(String.format("TỔNG TIỀN GIAO DỊCH: %,.0f VND", gd.getTongTien()));
        txtItemsDetail.setText(sb.toString());
        txtItemsDetail.setCaretPosition(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnBack)) {
            // Re-fetch stat option list to display the RevenueTableStatFrm again
            ReportDAO reportDAO = new ReportDAO();
            ArrayList<TableStat> listStat = reportDAO.getRevenueByTable(startDate, endDate);
            (new RevenueTableStatFrm(user, listStat, startDate, endDate)).setVisible(true);
            this.dispose();
        }
    }
}
