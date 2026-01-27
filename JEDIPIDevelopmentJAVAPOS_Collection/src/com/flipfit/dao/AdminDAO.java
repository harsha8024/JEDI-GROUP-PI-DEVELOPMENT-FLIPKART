package com.flipfit.dao;

import com.flipfit.bean.GymAdmin;
import com.flipfit.bean.Role;
import com.flipfit.database.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Access Object for Admin entity
 * Handles all database operations related to gym admins
 */
public class AdminDAO {
    
    private DatabaseConnection dbManager;
    
    public AdminDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }
    
    /**
     * Generate new admin ID from database counter
     */
    public synchronized String generateAdminId() {
        String sql = "UPDATE id_counters SET current_value = current_value + 1 WHERE counter_name = 'ADMIN'";
        String selectSql = "SELECT current_value FROM id_counters WHERE counter_name = 'ADMIN'";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery(selectSql);
            
            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "ADM" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating admin ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Save a new admin to database
     */
    public boolean saveAdmin(GymAdmin admin) {
        String sql = "INSERT INTO admins (admin_id, name, email, phone_number, city, password, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, admin.getUserID());
            pstmt.setString(2, admin.getName());
            pstmt.setString(3, admin.getEmail());
            pstmt.setString(4, admin.getPhoneNumber());
            pstmt.setString(5, admin.getCity());
            pstmt.setString(6, admin.getPassword());
            pstmt.setBoolean(7, true); // Admins are always active
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving admin: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get admin by email
     */
    public GymAdmin getAdminByEmail(String email) {
        String sql = "SELECT * FROM admins WHERE email = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToAdmin(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting admin by email: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get admin by ID
     */
    public GymAdmin getAdminById(String adminId) {
        String sql = "SELECT * FROM admins WHERE admin_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, adminId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToAdmin(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting admin by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all admins
     */
    public Map<String, GymAdmin> getAllAdmins() {
        Map<String, GymAdmin> admins = new HashMap<>();
        String sql = "SELECT * FROM admins";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                GymAdmin admin = mapResultSetToAdmin(rs);
                admins.put(admin.getEmail(), admin);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all admins: " + e.getMessage());
        }
        return admins;
    }
    
    /**
     * Update existing admin
     */
    public boolean updateAdmin(GymAdmin admin) {
        String sql = "UPDATE admins SET name = ?, phone_number = ?, city = ?, password = ?, " +
                     "is_active = ? WHERE email = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, admin.getName());
            pstmt.setString(2, admin.getPhoneNumber());
            pstmt.setString(3, admin.getCity());
            pstmt.setString(4, admin.getPassword());
            pstmt.setBoolean(5, true); // Admins are always active
            pstmt.setString(6, admin.getEmail());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating admin: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete admin by ID
     */
    public boolean deleteAdmin(String adminId) {
        String sql = "DELETE FROM admins WHERE admin_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, adminId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if email exists
     */
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM admins WHERE email = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking email existence: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Map ResultSet to GymAdmin object
     */
    private GymAdmin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        GymAdmin admin = new GymAdmin();
        admin.setUserID(rs.getString("admin_id"));
        admin.setName(rs.getString("name"));
        admin.setEmail(rs.getString("email"));
        admin.setPhoneNumber(rs.getString("phone_number"));
        admin.setCity(rs.getString("city"));
        admin.setPassword(rs.getString("password"));
        
        Role role = new Role();
        role.setRoleName("ADMIN");
        admin.setRole(role);
        
        admin.setActive(true); // Admins are always active
        
        return admin;
    }
}
