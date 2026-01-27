package com.flipfit.dao;

import com.flipfit.bean.GymAdmin;
import com.flipfit.bean.Role;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DatabaseConnection;

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
        try (Connection conn = dbManager.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(SQLConstants.UPDATE_COUNTER);
             PreparedStatement selectStmt = conn.prepareStatement(SQLConstants.SELECT_COUNTER)) {
            
            updateStmt.setString(1, "ADMIN");
            updateStmt.executeUpdate();
            
            selectStmt.setString(1, "ADMIN");
            ResultSet rs = selectStmt.executeQuery();
            
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
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INSERT_ADMIN)) {
            
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
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_ADMIN_BY_EMAIL)) {
            
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
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_ADMIN_BY_ID)) {
            
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
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_ALL_ADMINS)) {
            
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
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.UPDATE_ADMIN)) {
            
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
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DELETE_ADMIN)) {
            
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
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.COUNT_ADMIN_BY_EMAIL)) {
            
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
    
    // ==================== ADMIN-SPECIFIC ACTIONS ====================
    
    /**
     * Approve a gym (admin action)
     */
    public boolean approveGym(String gymId) {
        String sql = "UPDATE gyms SET is_approved = TRUE WHERE gym_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, gymId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error approving gym: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Reject a gym (admin action)
     */
    public boolean rejectGym(String gymId) {
        String sql = "UPDATE gyms SET is_approved = FALSE WHERE gym_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, gymId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error rejecting gym: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Approve a gym owner (admin action)
     */
    public boolean approveGymOwner(String ownerId) {
        String sql = "UPDATE gym_owners SET is_active = TRUE WHERE owner_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ownerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error approving gym owner: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Deactivate a gym owner (admin action)
     */
    public boolean deactivateGymOwner(String ownerId) {
        String sql = "UPDATE gym_owners SET is_active = FALSE WHERE owner_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ownerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deactivating gym owner: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Approve a customer (admin action)
     */
    public boolean approveCustomer(String customerId) {
        String sql = "UPDATE customers SET is_active = TRUE WHERE customer_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error approving customer: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Deactivate a customer (admin action)
     */
    public boolean deactivateCustomer(String customerId) {
        String sql = "UPDATE customers SET is_active = FALSE WHERE customer_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deactivating customer: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get count of all users (customers + gym owners + admins)
     */
    public Map<String, Integer> getUserCounts() {
        Map<String, Integer> counts = new HashMap<>();
        
        try (Connection conn = dbManager.getConnection()) {
            // Count customers
            String customerSql = "SELECT COUNT(*) as count FROM customers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(customerSql);
            if (rs.next()) {
                counts.put("customers", rs.getInt("count"));
            }
            
            // Count gym owners
            String ownerSql = "SELECT COUNT(*) as count FROM gym_owners";
            rs = stmt.executeQuery(ownerSql);
            if (rs.next()) {
                counts.put("gym_owners", rs.getInt("count"));
            }
            
            // Count admins
            String adminSql = "SELECT COUNT(*) as count FROM admins";
            rs = stmt.executeQuery(adminSql);
            if (rs.next()) {
                counts.put("admins", rs.getInt("count"));
            }
            
            // Total count
            int total = counts.getOrDefault("customers", 0) + 
                       counts.getOrDefault("gym_owners", 0) + 
                       counts.getOrDefault("admins", 0);
            counts.put("total", total);
            
        } catch (SQLException e) {
            System.err.println("Error getting user counts: " + e.getMessage());
        }
        
        return counts;
    }
    
    /**
     * Get count of pending gyms
     */
    public int getPendingGymsCount() {
        String sql = "SELECT COUNT(*) as count FROM gyms WHERE is_approved = FALSE";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error getting pending gyms count: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get count of inactive gym owners
     */
    public int getInactiveGymOwnersCount() {
        String sql = "SELECT COUNT(*) as count FROM gym_owners WHERE is_active = FALSE";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error getting inactive gym owners count: " + e.getMessage());
        }
        return 0;
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

