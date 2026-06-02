package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private String position;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, String fullName, String position) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    // Các hàm alias để dùng chung với module báo cáo thống kê.
    // Module thống kê cũ dùng tên tenNV/chucVu, project chính dùng fullName/position.
    public String getTenNV() {
        return fullName;
    }

    public void setTenNV(String tenNV) {
        this.fullName = tenNV;
    }

    public String getChucVu() {
        return position;
    }

    public void setChucVu(String chucVu) {
        this.position = chucVu;
    }

}
