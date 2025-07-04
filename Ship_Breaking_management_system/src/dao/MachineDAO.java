package dao;

import model.Machine;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MachineDAO {

    public void insert(Machine m) throws SQLException {
        String sql = "INSERT INTO Machines (machine_name, buying_price, buying_date, machine_weight_kg, bought_from) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getMachineName());
            ps.setDouble(2, m.getBuyingPrice());
            ps.setDate(3, Date.valueOf(m.getBuyingDate())); // Convert LocalDate to java.sql.Date
            ps.setDouble(4, m.getMachineWeightKg());
            ps.setString(5, m.getBoughtFrom());
            ps.executeUpdate();
        }
    }

    public void update(Machine m) throws SQLException {
        String sql = "UPDATE Machines SET machine_name=?, buying_price=?, buying_date=?, machine_weight_kg=?, bought_from=? WHERE machine_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getMachineName());
            ps.setDouble(2, m.getBuyingPrice());
            ps.setDate(3, Date.valueOf(m.getBuyingDate()));
            ps.setDouble(4, m.getMachineWeightKg());
            ps.setString(5, m.getBoughtFrom());
            ps.setInt(6, m.getMachineId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Machines WHERE machine_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Machine> getAll() throws SQLException {
        List<Machine> list = new ArrayList<>();
        String sql = "SELECT * FROM Machines";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Machine m = new Machine();
                m.setMachineId(rs.getInt("machine_id"));
                m.setMachineName(rs.getString("machine_name"));
                m.setBuyingPrice(rs.getDouble("buying_price"));
                Date date = rs.getDate("buying_date");
                m.setBuyingDate(date != null ? date.toLocalDate() : null);
                m.setMachineWeightKg(rs.getDouble("machine_weight_kg"));
                m.setBoughtFrom(rs.getString("bought_from"));
                list.add(m);
            }
        }
        return list;
    }
}
