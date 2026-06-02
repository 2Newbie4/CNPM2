package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Dish;

public class DishDAO extends DAO {

    public boolean addDish(Dish dish) {
        String sql = "INSERT INTO dishes(name, type, price, image, description, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, dish.getName());
            ps.setString(2, dish.getType());
            ps.setDouble(3, dish.getPrice());
            ps.setString(4, dish.getImage());
            ps.setString(5, dish.getDescription());
            ps.setString(6, dish.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateDish(Dish dish) {
        String sql = "UPDATE dishes SET name = ?, type = ?, price = ?, image = ?, description = ?, status = ? WHERE id = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, dish.getName());
            ps.setString(2, dish.getType());
            ps.setDouble(3, dish.getPrice());
            ps.setString(4, dish.getImage());
            ps.setString(5, dish.getDescription());
            ps.setString(6, dish.getStatus());
            ps.setInt(7, dish.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteDish(int id) {
        String sql = "DELETE FROM dishes WHERE id = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<Dish> searchDish(String keyword) {
        ArrayList<Dish> list = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllDishes();
        }

        String sql = "SELECT id, name, type, price, image, description, status FROM dishes "
                + "WHERE name LIKE ? OR type LIKE ? OR CAST(id AS VARCHAR(20)) LIKE ? ORDER BY id DESC";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            String key = "%" + keyword.trim() + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapDish(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Dish> getAllDishes() {
        ArrayList<Dish> list = new ArrayList<>();
        String sql = "SELECT id, name, type, price, image, description, status FROM dishes ORDER BY id DESC";

        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapDish(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Dish mapDish(ResultSet rs) throws Exception {
        return new Dish(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getDouble("price"),
                rs.getString("image"),
                rs.getString("description"),
                rs.getString("status")
        );
    }
}
