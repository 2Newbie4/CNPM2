package view.stat;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.ReportDAO;
import model.TableStat;
import model.User;

public class TimeRangeFrm extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField txtStart, txtEnd;
    private JButton btnStat, btnBack;
    private User user;

    public TimeRangeFrm(User user) {
        super("Chọn khoảng thời gian thống kê");
        this.user = user;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblTitle = new JLabel("NHẬP KHOẢNG THỜI GIAN THỐNG KÊ");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(18.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnStart = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnStart.add(new JLabel("Ngày bắt đầu (dd/MM/yyyy):"));
        txtStart = new JTextField("01/05/2026", 10);
        pnStart.add(txtStart);
        pnMain.add(pnStart);

        JPanel pnEnd = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnEnd.add(new JLabel("Ngày kết thúc (dd/MM/yyyy):"));
        txtEnd = new JTextField("31/05/2026", 10);
        pnEnd.add(txtEnd);
        pnMain.add(pnEnd);

        JPanel pnBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnStat = new JButton("Thống kê");
        btnBack = new JButton("Quay lại");
        pnBtn.add(btnStat);
        pnBtn.add(btnBack);
        pnMain.add(pnBtn);

        btnStat.addActionListener(this);
        btnBack.addActionListener(this);

        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setContentPane(pnMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnStat)) {
            String startStr = txtStart.getText().trim();
            String endStr = txtEnd.getText().trim();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // strict date parsing

            Date startDate = null;
            Date endDate = null;

            try {
                startDate = sdf.parse(startStr);
                // Adjust start date to the beginning of the day (00:00:00)
                startDate.setHours(0);
                startDate.setMinutes(0);
                startDate.setSeconds(0);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không đúng định dạng dd/MM/yyyy!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                endDate = sdf.parse(endStr);
                // Adjust end date to the end of the day (23:59:59)
                endDate.setHours(23);
                endDate.setMinutes(59);
                endDate.setSeconds(59);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc không đúng định dạng dd/MM/yyyy!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validation: endDate < startDate
            if (endDate.before(startDate)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu!", "Lỗi ngày nhập", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ReportDAO reportDAO = new ReportDAO();
            ArrayList<TableStat> listStat = reportDAO.getRevenueByTable(startDate, endDate);

            if (listStat == null || listStat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu trong khoảng thời gian đã chọn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                (new RevenueTableStatFrm(user, listStat, startDate, endDate)).setVisible(true);
                this.dispose();
            }
        } else if (e.getSource().equals(btnBack)) {
            (new StatOptionFrm(user)).setVisible(true);
            this.dispose();
        }
    }
}
