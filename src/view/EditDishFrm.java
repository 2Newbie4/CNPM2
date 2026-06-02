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

public class EditDishFrm extends JFrame {
    private ManageDishFrm parent;
    private SearchDishFrm searchFrm;
    private Dish originalDish;

    private JTextField txtId;
    private JTextField txtName;
    private JComboBox<String> cbbType;
    private JTextField txtPrice;
    private JTextField txtImage;
    private JTextArea txtDescription;
    private JComboBox<String> cbbStatus;
    private JButton btnSave;
    private JButton btnReset;
    private JButton btnBack;

    public EditDishFrm(ManageDishFrm parent, SearchDishFrm searchFrm, Dish dish) {
        this.parent = parent;
        this.searchFrm = searchFrm;
        this.originalDish = dish;
        initComponents();
        showDish(dish);
    }

    private void initComponents() {
        setTitle("Sửa món ăn");
        setSize(560, 470);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(25);
        txtId.setEditable(false);
        panel.add(txtId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tên món:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(25);
        panel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Danh mục:"), gbc);
        gbc.gridx = 1;
        cbbType = new JComboBox<>(new String[]{"Khai vị", "Món chính", "Đồ uống", "Tráng miệng"});
        panel.add(cbbType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1;
        txtPrice = new JTextField(25);
        panel.add(txtPrice, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Hình ảnh:"), gbc);
        gbc.gridx = 1;
        txtImage = new JTextField(25);
        panel.add(txtImage, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1;
        txtDescription = new JTextArea(4, 25);
        panel.add(new JScrollPane(txtDescription), gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1;
        cbbStatus = new JComboBox<>(new String[]{"Hiện", "Ẩn"});
        panel.add(cbbStatus, gbc);

        JPanel buttonPanel = new JPanel();
        btnSave = new JButton("Cập nhật");
        btnReset = new JButton("Reset");
        btnBack = new JButton("Quay lại");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnBack);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> updateDish());
        btnReset.addActionListener(e -> showDish(originalDish));
        btnBack.addActionListener(e -> backToSearch());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                backToSearch();
            }
        });
    }

    private void showDish(Dish dish) {
        txtId.setText(String.valueOf(dish.getId()));
        txtName.setText(dish.getName());
        cbbType.setSelectedItem(dish.getType());
        txtPrice.setText(String.valueOf(dish.getPrice()));
        txtImage.setText(dish.getImage());
        txtDescription.setText(dish.getDescription());
        cbbStatus.setSelectedItem(dish.getStatus());
    }

    private void updateDish() {
        Dish dish = getDishFromForm();
        if (dish == null) {
            return;
        }

        boolean success = new DishDAO().updateDish(dish);
        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật món ăn thành công.");
            parent.refreshTable();
            parent.setVisible(true);
            searchFrm.dispose();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật món ăn thất bại.");
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

        return new Dish(originalDish.getId(), name, type, price, image, description, status);
    }

    private void backToSearch() {
        searchFrm.setVisible(true);
        dispose();
    }
}
