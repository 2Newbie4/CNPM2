package view;

import dao.DishDAO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Dish;

public class ManageDishFrm extends JFrame {
    private JButton btnAddDish;
    private JButton btnEditDish;
    private JButton btnDeleteDish;
    private JButton btnRefresh;
    private JTable tblDish;
    private DefaultTableModel tableModel;
    private DishDAO dishDAO;

    public ManageDishFrm() {
        dishDAO = new DishDAO();
        initComponents();
        loadDishes();
    }

    private void initComponents() {
        setTitle("Quản lý món ăn");
        setSize(780, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAddDish = new JButton("Thêm món ăn");
        btnEditDish = new JButton("Sửa món ăn");
        btnDeleteDish = new JButton("Xóa món ăn");
        btnRefresh = new JButton("Làm mới");

        topPanel.add(btnAddDish);
        topPanel.add(btnEditDish);
        topPanel.add(btnDeleteDish);
        topPanel.add(btnRefresh);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Tên món", "Danh mục", "Đơn giá", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDish = new JTable(tableModel);
        add(new JScrollPane(tblDish), BorderLayout.CENTER);

        btnAddDish.addActionListener(e -> {
            new AddDishFrm(this).setVisible(true);
            setVisible(false);
        });

        btnEditDish.addActionListener(e -> {
            new SearchDishFrm(this, SearchDishFrm.MODE_EDIT).setVisible(true);
            setVisible(false);
        });

        btnDeleteDish.addActionListener(e -> {
            new SearchDishFrm(this, SearchDishFrm.MODE_DELETE).setVisible(true);
            setVisible(false);
        });

        btnRefresh.addActionListener(e -> loadDishes());
    }

    public void refreshTable() {
        loadDishes();
    }

    private void loadDishes() {
        tableModel.setRowCount(0);
        ArrayList<Dish> list = dishDAO.getAllDishes();

        for (Dish dish : list) {
            tableModel.addRow(new Object[]{
                dish.getId(),
                dish.getName(),
                dish.getType(),
                dish.getPrice(),
                dish.getStatus()
            });
        }

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa có món ăn nào trong CSDL.");
        }
    }
}
