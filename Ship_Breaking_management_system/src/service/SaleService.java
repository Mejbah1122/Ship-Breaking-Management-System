package service;

import dao.PartSaleDAO;
import dao.SalesEventDAO;
import model.PartSale;
import model.SalesEvent;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SaleService {

    private SalesEventDAO salesEventDAO = new SalesEventDAO();
    private PartSaleDAO partSaleDAO = new PartSaleDAO();

    /**
     * Insert a sale event and related part sales atomically.
     * @param buyerName buyer's name
     * @param saleDate date of sale
     * @param notes sale notes
     * @param partSales list of PartSale objects (without saleId set)
     * @throws SQLException
     */
    public void createSaleWithParts(String buyerName, LocalDate saleDate, String notes, List<PartSale> partSales) throws SQLException {
        Connection conn = null;
        try {
            conn = dao.DBConnection.getConnection();
            conn.setAutoCommit(false); // begin transaction

            // 1. Insert sale event and get generated sale_id
            SalesEvent saleEvent = new SalesEvent(buyerName, saleDate, notes);
            int saleId = insertSaleEvent(conn, saleEvent);

            // 2. Insert each part sale linked to saleId
            for (PartSale ps : partSales) {
                ps.setSaleId(saleId);
                insertPartSale(conn, ps);
            }

            conn.commit(); // commit transaction
        } catch (SQLException ex) {
            if (conn != null) conn.rollback(); // rollback on error
            throw ex;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
            if (conn != null) conn.close();
        }
    }

    // Helper method to insert sale event with existing connection and return saleId
    private int insertSaleEvent(Connection conn, SalesEvent se) throws SQLException {
        String sql = "INSERT INTO SalesEvents (buyer_name, sale_date, notes) VALUES (?, ?, ?)";
        try (var ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, se.getBuyerName());
            ps.setDate(2, java.sql.Date.valueOf(se.getSaleDate()));
            ps.setString(3, se.getNotes());
            ps.executeUpdate();
            try (var rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Failed to retrieve sale_id");
            }
        }
    }

    // Helper method to insert part sale with existing connection
    private void insertPartSale(Connection conn, PartSale ps) throws SQLException {
        String sql = "INSERT INTO PartSales (part_id, sale_id, quantity, selling_price, melting_price) VALUES (?, ?, ?, ?, ?)";
        try (var psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, ps.getPartId());
            psmt.setInt(2, ps.getSaleId());
            psmt.setInt(3, ps.getQuantity());
            psmt.setDouble(4, ps.getSellingPrice());
            psmt.setDouble(5, ps.getMeltingPrice());
            psmt.executeUpdate();
        }
    }
}
