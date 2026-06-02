package view.stat;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.User;
import view.ManagerHomeFrm;

public class StatOptionFrm extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> cbType, cbStyle;
    private JButton btnContinue, btnBack;
    private User user;

    public StatOptionFrm(User user) {
        super("Chọn tiêu chí thống kê");
        this.user = user;

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.PAGE_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblTitle = new JLabel("LỰA CHỌN TIÊU CHÍ THỐNG KÊ");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(18.0f));
        pnMain.add(lblTitle);
        pnMain.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel pnType = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnType.add(new JLabel("Loại thống kê:"));
        String[] types = {"Theo bàn", "Khách hàng (Chưa hỗ trợ)", "Dịch vụ (Chưa hỗ trợ)"};
        cbType = new JComboBox<>(types);
        pnType.add(cbType);
        pnMain.add(pnType);

        JPanel pnStyle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnStyle.add(new JLabel("Kiểu thống kê:"));
        String[] styles = {"Doanh thu", "Thời gian (Chưa hỗ trợ)"};
        cbStyle = new JComboBox<>(styles);
        pnStyle.add(cbStyle);
        pnMain.add(pnStyle);

        JPanel pnBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnContinue = new JButton("Tiếp tục");
        btnBack = new JButton("Quay lại");
        pnBtn.add(btnContinue);
        pnBtn.add(btnBack);
        pnMain.add(pnBtn);

        btnContinue.addActionListener(this);
        btnBack.addActionListener(this);

        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setContentPane(pnMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnContinue)) {
            int typeIdx = cbType.getSelectedIndex();
            int styleIdx = cbStyle.getSelectedIndex();

            if (typeIdx == 0 && styleIdx == 0) { // Theo bàn & Doanh thu
                (new TimeRangeFrm(user)).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tiêu chí thống kê này đang được phát triển!");
            }
        } else if (e.getSource().equals(btnBack)) {
            (new ManagerHomeFrm(user)).setVisible(true);
            this.dispose();
        }
    }
}
