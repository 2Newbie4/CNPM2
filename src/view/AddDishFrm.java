package view;

import dao.DishDAO;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Dish;

public class AddDishFrm extends JFrame {
    private ManageDishFrm parent;
    private JTextField txtName;
    private JComboBox<String> cbbType;
    private JTextField txtPrice;
    private JTextField txtImage;
    private JTextArea txtDescription;
    private JComboBox<String> cbbStatus;
    private JButton btnSave;
    private JButton btnCancel;

    public AddDishFrm(ManageDishFrm parent) {
        this.parent = parent;
        initComponents();
    }

    private void initComponents() {
        setTitle("Thêm món ăn");
        setSize(560, 430);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Tên món:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(25);
        panel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Danh mục:"), gbc);
        gbc.gridx = 1;
        cbbType = new JComboBox<>(new String[]{"Khai vị", "Món chính", "Đồ uống", "Tráng miệng"});
        panel.add(cbbType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1;
        txtPrice = new JTextField(25);
        panel.add(txtPrice, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Hình ảnh:"), gbc);
        gbc.gridx = 1;
        txtImage = new JTextField(25);
        panel.add(txtImage, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1;
        txtDescription = new JTextArea(4, 25);
        panel.add(new JScrollPane(txtDescription), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1;
        cbbStatus = new JComboBox<>(new String[]{"Hiện", "Ẩn"});
        panel.add(cbbStatus, gbc);

        JPanel buttonPanel = new JPanel();
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> saveDish());
        btnCancel.addActionListener(e -> backToManage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                backToManage();
            }
        });
    }

    private void saveDish() {
        Dish dish = getDishFromForm();
        if (dish == null) {
            return;
        }

        boolean success = new DishDAO().addDish(dish);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm món ăn thành công.");
            parent.refreshTable();
            backToManage();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm món ăn thất bại.");
        }
    }

    private Dish getDishFromForm() {
        String name = txtName.getText().trim();
        String type = cbbType.getSelectedItem().toString();
        String priceText = txtPrice.getText().trim();
        String image = txtImage.getText().trim();
        String description = txtDescription.getText().trim();
        String status = cbbStatus.getSelectedItem().toString();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên món không được rỗng.");
            return null;
        }

        if (type.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Danh mục không được rỗng.");
            return null;
        }

        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá phải là số.");
            return null;
        }

        if (price <= 0) {
            JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0.");
            return null;
        }

        return new Dish(name, type, price, image, description, status);
    }

    private void backToManage() {
        parent.setVisible(true);
        dispose();
    }
}
