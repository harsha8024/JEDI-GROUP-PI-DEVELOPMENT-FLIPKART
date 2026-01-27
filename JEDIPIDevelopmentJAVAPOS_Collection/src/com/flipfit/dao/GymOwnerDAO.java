package com.flipfit.dao;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Role;
import com.flipfit.database.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Access Object for GymOwner entity
 * Handles all database operations related to gym owners
 */
public class GymOwnerDAO {
    
    private DatabaseConnection dbManager;
    
    public GymOwnerDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }
    
    /**
     * Generate new owner ID from database counter
     */
    public synchronized String generateOwnerId() {
        String sql = "UPDATE id_counters SET current_value = current_value + 1 WHERE counter_name = 'OWNER'";
        String selectSql = "SELECT current_value FROM id_counters WHERE counter_name = 'OWNER'";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery(selectSql);
            
            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "OWN" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating owner ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Save a new gym owner to database
     */
    public boolean saveGymOwner(GymOwner owner) {
        String sql = "INSERT INTO gym_owners (owner_id, name, email, phone_number, city, password, " +
                     "pan_number, aadhar_number, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, owner.getUserID());
            pstmt.setString(2, owner.getName());
            pstmt.setString(3, owner.getEmail());
            pstmt.setString(4, owner.getPhoneNumber());
            pstmt.setString(5, owner.getCity());
            pstmt.setString(6, owner.getPassword());
            pstmt.setString(7, owner.getPanNumber());
            pstmt.setString(8, owner.getAadharNumber());
            pstmt.setBoolean(9, owner.isActive());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving gym owner: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get gym owner by email
     */
    public GymOwner getGymOwnerByEmail(String email) {
        String sql = "SELECT * FROM gym_owners WHERE email = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToGymOwner(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting gym owner by email: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get gym owner by ID
     */
    public GymOwner getGymOwnerById(String ownerId) {
        String sql = "SELECT * FROM gym_owners WHERE owner_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ownerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToGymOwner(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting gym owner by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all gym owners
     */
    public Map<String, GymOwner> getAllGymOwners() {
        Map<String, GymOwner> owners = new HashMap<>();
        String sql = "SELECT * FROM gym_owners";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                GymOwner owner = mapResultSetToGymOwner(rs);
                owners.put(owner.getEmail(), owner);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all gym owners: " + e.getMessage());
        }
        return owners;
    }
    
    /**
     * Update existing gym owner
     */
    public boolean updateGymOwner(GymOwner owner) {
        String sql = "UPDATE gym_owners SET name = ?, phone_number = ?, city = ?, password = ?, " +
                     "pan_number = ?, aadhar_number = ?, is_active = ? WHERE email = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, owner.getName());
            pstmt.setString(2, owner.getPhoneNumber());
            pstmt.setString(3, owner.getCity());
            pstmt.setString(4, owner.getPassword());
            pstmt.setString(5, owner.getPanNumber());
            pstmt.setString(6, owner.getAadharNumber());
            pstmt.setBoolean(7, owner.isActive());
            pstmt.setString(8, owner.getEmail());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating gym owner: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete gym owner by ID
     */
    public boolean deleteGymOwner(String ownerId) {
        String sql = "DELETE FROM gym_owners WHERE owner_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, ownerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting gym owner: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if email exists
     */
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM gym_owners WHERE email = ?";
        
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
     * Map ResultSet to GymOwner object
     */
    private GymOwner mapResultSetToGymOwner(ResultSet rs) throws SQLException {
        GymOwner owner = new GymOwner();
        owner.setUserID(rs.getString("owner_id"));
        owner.setName(rs.getString("name"));
        owner.setEmail(rs.getString("email"));
        owner.setPhoneNumber(rs.getString("phone_number"));
        owner.setCity(rs.getString("city"));
        owner.setPassword(rs.getString("password"));
        owner.setPanNumber(rs.getString("pan_number"));
        owner.setAadharNumber(rs.getString("aadhar_number"));
        
        Role role = new Role();
        role.setRoleName("OWNER");
        owner.setRole(role);
        
        owner.setActive(rs.getBoolean("is_active"));
        
        return owner;
    }
}
