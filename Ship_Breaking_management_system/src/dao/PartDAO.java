package dao;

import model.Part;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartDAO {

    public void insert(Part p) throws SQLException {
        String sql = "INSERT INTO Parts (machine_id, part_name, category, weight_kg, wastage_weight_kg, min_selling_price, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getMachineId());
            ps.setString(2, p.getPartName());
            ps.setString(3, p.getCategory());
            ps.setDouble(4, p.getWeightKg());
            ps.setDouble(5, p.getWastageWeightKg());
            ps.setDouble(6, p.getMinSellingPrice());  // Added
            ps.setString(7, p.getStatus());
            ps.executeUpdate();
        }
    }

    public void update(Part p) throws SQLException {
        String sql = "UPDATE Parts SET machine_id=?, part_name=?, category=?, weight_kg=?, wastage_weight_kg=?, min_selling_price=?, status=? WHERE part_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getMachineId());
            ps.setString(2, p.getPartName());
            ps.setString(3, p.getCategory());
            ps.setDouble(4, p.getWeightKg());
            ps.setDouble(5, p.getWastageWeightKg());
            ps.setDouble(6, p.getMinSellingPrice());  // Added
            ps.setString(7, p.getStatus());
            ps.setInt(8, p.getPartId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Parts WHERE part_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Part> getAll() throws SQLException {
        List<Part> list = new ArrayList<>();
        String sql = "SELECT * FROM Parts";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Part p = new Part();
                p.setPartId(rs.getInt("part_id"));
                p.setMachineId(rs.getInt("machine_id"));
                p.setPartName(rs.getString("part_name"));
                p.setCategory(rs.getString("category"));
                p.setWeightKg(rs.getDouble("weight_kg"));
                p.setWastageWeightKg(rs.getDouble("wastage_weight_kg"));
                p.setMinSellingPrice(rs.getDouble("min_selling_price"));  // Added
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        }
        return list;
    }
}
