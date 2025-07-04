package dao;

import model.SalesEvent;
import util.Database;  // Your DB connection helper

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesEventDAO {

    // Insert sale and get generated sale_id
    public int insertAndGetId(SalesEvent se) throws SQLException {
        String sql = "INSERT INTO SalesEvents (buyer_name, sale_date, notes) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, se.getBuyerName());
            ps.setDate(2, Date.valueOf(se.getSaleDate()));  // convert LocalDate to java.sql.Date
            ps.setString(3, se.getNotes());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating sale event failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating sale event failed, no ID obtained.");
                }
            }
        }
    }

    // Insert sale without returning ID
    public void insert(SalesEvent se) throws SQLException {
        String sql = "INSERT INTO SalesEvents (buyer_name, sale_date, notes) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, se.getBuyerName());
            ps.setDate(2, Date.valueOf(se.getSaleDate()));
            ps.setString(3, se.getNotes());
            ps.executeUpdate();
        }
    }

    // Update sale event
    public void update(SalesEvent se) throws SQLException {
        String sql = "UPDATE SalesEvents SET buyer_name = ?, sale_date = ?, notes = ? WHERE sale_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, se.getBuyerName());
            ps.setDate(2, Date.valueOf(se.getSaleDate()));
            ps.setString(3, se.getNotes());
            ps.setInt(4, se.getSaleId());
            ps.executeUpdate();
        }
    }

    // Delete sale event
    public void delete(int saleId) throws SQLException {
        String sql = "DELETE FROM SalesEvents WHERE sale_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, saleId);
            ps.executeUpdate();
        }
    }

    // Get sale by id
    public SalesEvent getById(int saleId) throws SQLException {
        String sql = "SELECT * FROM SalesEvents WHERE sale_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, saleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSalesEvent(rs);
                }
            }
        }
        return null;
    }

    // Get all sales
    public List<SalesEvent> getAll() throws SQLException {
        List<SalesEvent> salesList = new ArrayList<>();
        String sql = "SELECT * FROM SalesEvents ORDER BY sale_date DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                salesList.add(mapResultSetToSalesEvent(rs));
            }
        }
        return salesList;
    }

    // Helper to map ResultSet to SalesEvent object
    private SalesEvent mapResultSetToSalesEvent(ResultSet rs) throws SQLException {
        int saleId = rs.getInt("sale_id");
        String buyerName = rs.getString("buyer_name");
        LocalDate saleDate = rs.getDate("sale_date").toLocalDate();
        String notes = rs.getString("notes");
        return new SalesEvent(saleId, buyerName, saleDate, notes);
    }
}
