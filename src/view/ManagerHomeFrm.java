package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.User;
import view.stat.StatOptionFrm;

public class ManagerHomeFrm extends JFrame {
    private User currentUser;
    private JButton btnManageDish;
    private JButton btnReport;
    private JButton btnLogout;

    public ManagerHomeFrm(User currentUser) {
        this.currentUser = currentUser;
        initComponents();
    }

    private void initComponents() {
        setTitle("Màn hình chính quản lý");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblWelcome = new JLabel("Xin chào: " + currentUser.getFullName() + " - " + currentUser.getPosition(), JLabel.CENTER);
        add(lblWelcome, BorderLayout.NORTH);

        JPanel panel = new JPanel(new FlowLayout());
        btnManageDish = new JButton("Quản lý món ăn");
        btnReport = new JButton("Báo cáo thống kê");
        btnLogout = new JButton("Đăng xuất");
        panel.add(btnManageDish);
        panel.add(btnReport);
        panel.add(btnLogout);
        add(panel, BorderLayout.CENTER);

        btnManageDish.addActionListener(e -> {
            new ManageDishFrm().setVisible(true);
            dispose();
        });

        btnReport.addActionListener(e -> {
            new StatOptionFrm(currentUser).setVisible(true);
            dispose();
        });

        btnLogout.addActionListener(e -> {
            new LoginFrm().setVisible(true);
            dispose();
        });
    }
}
