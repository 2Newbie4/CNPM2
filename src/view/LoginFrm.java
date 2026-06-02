package view;

import dao.UserDAO;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.User;

public class LoginFrm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrm() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Đăng nhập hệ thống");
        setSize(420, 230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP QUẢN LÝ", JLabel.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        panel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Mật khẩu:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        panel.add(txtPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        btnLogin = new JButton("Đăng nhập");
        panel.add(btnLogin, gbc);

        add(panel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> login());
        txtPassword.addActionListener(e -> login());
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đăng nhập và mật khẩu.");
            return;
        }

        User loginUser = new User(username, password);
        User result = new UserDAO().checkLogin(loginUser);

        if (result != null) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công.");
            new ManagerHomeFrm(result).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu.");
        }
    }
}
