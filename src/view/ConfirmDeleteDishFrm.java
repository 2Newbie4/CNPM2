package view;

import dao.DishDAO;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Dish;

public class ConfirmDeleteDishFrm extends JFrame {
    private ManageDishFrm parent;
    private SearchDishFrm searchFrm;
    private Dish dish;
    private JButton btnConfirm;
    private JButton btnCancel;

    public ConfirmDeleteDishFrm(ManageDishFrm parent, SearchDishFrm searchFrm, Dish dish) {
        this.parent = parent;
        this.searchFrm = searchFrm;
        this.dish = dish;
        initComponents();
    }

    private void initComponents() {
        setTitle("Xác nhận xóa món ăn");
        setSize(460, 240);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(5, 1));
        infoPanel.add(new JLabel("Bạn có chắc chắn muốn xóa món này không?", JLabel.CENTER));
        infoPanel.add(new JLabel("ID: " + dish.getId(), JLabel.CENTER));
        infoPanel.add(new JLabel("Tên món: " + dish.getName(), JLabel.CENTER));
        infoPanel.add(new JLabel("Danh mục: " + dish.getType(), JLabel.CENTER));
        infoPanel.add(new JLabel("Trạng thái: " + dish.getStatus(), JLabel.CENTER));
        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        btnConfirm = new JButton("Xác nhận");
        btnCancel = new JButton("Hủy");
        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        btnConfirm.addActionListener(e -> deleteDish());
        btnCancel.addActionListener(e -> backToSearch());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                backToSearch();
            }
        });
    }

    private void deleteDish() {
        boolean success = new DishDAO().deleteDish(dish.getId());
        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa món ăn thành công.");
            parent.refreshTable();
            parent.setVisible(true);
            searchFrm.dispose();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa món ăn thất bại. Có thể món đang được sử dụng ở dữ liệu khác.");
        }
    }

    private void backToSearch() {
        searchFrm.setVisible(true);
        dispose();
    }
}
