package view;

import dao.DishDAO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Dish;

public class SearchDishFrm extends JFrame {
    public static final int MODE_EDIT = 1;
    public static final int MODE_DELETE = 2;

    private ManageDishFrm parent;
    private int mode;
    private JTextField txtKeyword;
    private JButton btnSearch;
    private JButton btnBack;
    private JTable tblDish;
    private DefaultTableModel tableModel;
    private ArrayList<Dish> currentList;
    private DishDAO dishDAO;

    public SearchDishFrm(ManageDishFrm parent, int mode) {
        this.parent = parent;
        this.mode = mode;
        this.dishDAO = new DishDAO();
        this.currentList = new ArrayList<>();
        initComponents();
        searchDish();
    }

    private void initComponents() {
        setTitle(mode == MODE_EDIT ? "Tìm món ăn để sửa" : "Tìm món ăn để xóa");
        setSize(760, 430);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Từ khóa:"));
        txtKeyword = new JTextField(25);
        btnSearch = new JButton("Tìm kiếm");
        btnBack = new JButton("Quay lại");
        topPanel.add(txtKeyword);
        topPanel.add(btnSearch);
        topPanel.add(btnBack);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Tên món", "Danh mục", "Đơn giá", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDish = new JTable(tableModel);
        add(new JScrollPane(tblDish), BorderLayout.CENTER);

        JLabel bottom = new JLabel("Double click vào một dòng để chọn món.", JLabel.CENTER);
        add(bottom, BorderLayout.SOUTH);

        btnSearch.addActionListener(e -> searchDish());
        txtKeyword.addActionListener(e -> searchDish());
        btnBack.addActionListener(e -> backToManage());

        tblDish.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelectedDish();
                }
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                backToManage();
            }
        });
    }

    private void searchDish() {
        String keyword = txtKeyword.getText().trim();
        currentList = dishDAO.searchDish(keyword);

        tableModel.setRowCount(0);
        for (Dish dish : currentList) {
            tableModel.addRow(new Object[]{
                dish.getId(),
                dish.getName(),
                dish.getType(),
                dish.getPrice(),
                dish.getStatus()
            });
        }

        if (currentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có món nào phù hợp với từ khóa tìm kiếm.");
        }
    }

    private void openSelectedDish() {
        int row = tblDish.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một món ăn.");
            return;
        }

        Dish selectedDish = currentList.get(row);
        if (mode == MODE_EDIT) {
            new EditDishFrm(parent, this, selectedDish).setVisible(true);
            setVisible(false);
        } else if (mode == MODE_DELETE) {
            new ConfirmDeleteDishFrm(parent, this, selectedDish).setVisible(true);
            setVisible(false);
        }
    }

    private void backToManage() {
        parent.setVisible(true);
        dispose();
    }
}
