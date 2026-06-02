package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    protected static Connection con;

    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=restaurant_pos;encrypt=true;trustServerCertificate=true;";

    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "123456";

    public DAO() {
        connect();
    }

    private static void connect() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy SQL Server JDBC Driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Không kết nối được CSDL. Kiểm tra DB_URL, DB_USER, DB_PASSWORD.");
            e.printStackTrace();
        }
    }

    protected Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            connect();
        }
        return con;
    }
}
