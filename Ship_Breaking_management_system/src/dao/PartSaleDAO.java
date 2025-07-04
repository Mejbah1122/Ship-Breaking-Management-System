package dao;

import model.PartSale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartSaleDAO {

    public void insert(PartSale ps) throws SQLException {
        String sql = "INSERT INTO PartSales (part_id, sale_id, quantity, selling_price, melting_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, ps.getPartId());
            psmt.setInt(2, ps.getSaleId());
            psmt.setInt(3, ps.getQuantity());
            psmt.setDouble(4, ps.getSellingPrice());
            psmt.setDouble(5, ps.getMeltingPrice());
            psmt.executeUpdate();
        }
    }

    public void update(PartSale ps) throws SQLException {
        String sql = "UPDATE PartSales SET part_id=?, sale_id=?, quantity=?, selling_price=?, melting_price=? WHERE part_sale_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, ps.getPartId());
            psmt.setInt(2, ps.getSaleId());
            psmt.setInt(3, ps.getQuantity());
            psmt.setDouble(4, ps.getSellingPrice());
            psmt.setDouble(5, ps.getMeltingPrice());
            psmt.setInt(6, ps.getPartSaleId());
            psmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM PartSales WHERE part_sale_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, id);
            psmt.executeUpdate();
        }
    }

    public List<PartSale> getAll() throws SQLException {
        List<PartSale> list = new ArrayList<>();
        String sql = "SELECT * FROM PartSales";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PartSale ps = new PartSale();
                ps.setPartSaleId(rs.getInt("part_sale_id"));
                ps.setPartId(rs.getInt("part_id"));
                ps.setSaleId(rs.getInt("sale_id"));
                ps.setQuantity(rs.getInt("quantity"));
                ps.setSellingPrice(rs.getDouble("selling_price"));
                ps.setMeltingPrice(rs.getDouble("melting_price"));
                list.add(ps);
            }
        }
        return list;
    }
}
