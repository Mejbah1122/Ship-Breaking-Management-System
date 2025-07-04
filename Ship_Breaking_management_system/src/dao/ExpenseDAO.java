package dao;

import model.Expense;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    public void insert(Expense e) throws SQLException {
        String sql = "INSERT INTO Expenses (machine_id, crane_fee, delivery_fee, labor_fee, broker_fee, wastage_cost, expense_date, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, e.getMachineId());
            ps.setDouble(2, e.getCraneFee());
            ps.setDouble(3, e.getDeliveryFee());
            ps.setDouble(4, e.getLaborFee());
            ps.setDouble(5, e.getBrokerFee());
            ps.setDouble(6, e.getWastageCost());
            ps.setDate(7, Date.valueOf(e.getExpenseDate()));
            ps.setString(8, e.getNotes());
            ps.executeUpdate();
        }
    }

    public void update(Expense e) throws SQLException {
        String sql = "UPDATE Expenses SET machine_id=?, crane_fee=?, delivery_fee=?, labor_fee=?, broker_fee=?, wastage_cost=?, expense_date=?, notes=? WHERE expense_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, e.getMachineId());
            ps.setDouble(2, e.getCraneFee());
            ps.setDouble(3, e.getDeliveryFee());
            ps.setDouble(4, e.getLaborFee());
            ps.setDouble(5, e.getBrokerFee());
            ps.setDouble(6, e.getWastageCost());
            ps.setDate(7, Date.valueOf(e.getExpenseDate()));
            ps.setString(8, e.getNotes());
            ps.setInt(9, e.getExpenseId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Expenses WHERE expense_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Expense> getAll() throws SQLException {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM Expenses ORDER BY expense_date DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Expense e = new Expense();
                e.setExpenseId(rs.getInt("expense_id"));
                e.setMachineId(rs.getInt("machine_id"));
                e.setCraneFee(rs.getDouble("crane_fee"));
                e.setDeliveryFee(rs.getDouble("delivery_fee"));
                e.setLaborFee(rs.getDouble("labor_fee"));
                e.setBrokerFee(rs.getDouble("broker_fee"));
                e.setWastageCost(rs.getDouble("wastage_cost"));
                e.setExpenseDate(rs.getDate("expense_date").toLocalDate());
                e.setNotes(rs.getString("notes"));
                list.add(e);
            }
        }
        return list;
    }
}
