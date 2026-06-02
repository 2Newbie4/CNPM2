package view.stat;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.DefaultTableModel;

import dao.ReportDAO;
import model.GiaoDich;
import model.TableStat;
import model.User;

public class RevenueTableStatFrm extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private ArrayList<TableStat> listStat;
    private Date startDate, endDate;
    private JTable tblResult;
    private JButton btnBack;
    private User user;
    private RevenueTableStatFrm mainFrm;

    public RevenueTableStatFrm(User user, ArrayList<TableStat> listStat, Date startDate, Date endDate) {
        super("Thống kê doanh thu theo bàn");
        this.user = user;
        this.listStat = listStat;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mainFrm = this;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        JLabel lblTitle = new JLabel("BÁO CÁO DOANH THU THEO BÀN");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(20.0f));
        pnMain.add(lblTitle);

        JLabel lblTime = new JLabel("Khoảng thời gian: " + sdf.format(startDate) + " - " + sdf.format(endDate));
        lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnMain.add(lblTime);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        // Setup Table
        String[] columnNames = {"STT", "Tên bàn", "Kiểu", "Khu vực", "Tổng lượt khách", "Doanh thu (VND)"};
        Object[][] data = new Object[listStat.size()][6];
        for (int i = 0; i < listStat.size(); i++) {
            TableStat item = listStat.get(i);
            data[i][0] = (i + 1);
            data[i][1] = item.getBan().getTenBan();
            data[i][2] = item.getBan().getKieu();
            data[i][3] = item.getBan().getKhuVuc();
            data[i][4] = item.getTongLuotKhach();
            data[i][5] = String.format("%,.0f", item.getDoanhThu());
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblResult = new JTable(tableModel);
        tblResult.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(tblResult);
        scrollPane.setPreferredSize(new Dimension(550, 200));

        tblResult.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblResult.getSelectedRow();
                if (row >= 0 && row < listStat.size()) {
                    TableStat selectedStat = listStat.get(row);
                    int tableId = selectedStat.getBan().getId();
                    
                    ReportDAO reportDAO = new ReportDAO();
                    ArrayList<GiaoDich> listGiaoDich = reportDAO.getDetailRevenueByTable(tableId, startDate, endDate);
                    
                    (new RevenueDetailFrm(user, listGiaoDich, selectedStat, startDate, endDate)).setVisible(true);
                    mainFrm.dispose();
                }
            }
        });

        JPanel pnTable = new JPanel(new BorderLayout());
        pnTable.add(scrollPane, BorderLayout.CENTER);
        pnMain.add(pnTable);
        pnMain.add(Box.createRigidArea(new Dimension(0, 10)));

        btnBack = new JButton("Quay lại");
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.addActionListener(this);
        pnMain.add(btnBack);
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        this.setContentPane(pnMain);
        this.setSize(650, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnBack)) {
            (new TimeRangeFrm(user)).setVisible(true);
            this.dispose();
        }
    }
}
