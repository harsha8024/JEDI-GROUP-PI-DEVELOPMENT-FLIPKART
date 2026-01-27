package com.flipfit.dao;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.Role;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Access Object for Customer entity
 * Handles all database operations related to gym customers
 */
public class CustomerDAO {
    
    private DatabaseConnection dbManager;
    
    public CustomerDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }
    
    /**
     * Generate new customer ID from database counter
     */
    public synchronized String generateCustomerId() {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(SQLConstants.UPDATE_COUNTER);
             PreparedStatement selectStmt = conn.prepareStatement(SQLConstants.SELECT_COUNTER)) {
            
            updateStmt.setString(1, "CUSTOMER");
            updateStmt.executeUpdate();
            
            selectStmt.setString(1, "CUSTOMER");
            ResultSet rs = selectStmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "CUST" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating customer ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Save a new customer to database
     */
    public boolean saveCustomer(GymCustomer customer) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INSERT_CUSTOMER)) {
            
            pstmt.setString(1, customer.getUserID());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.setString(5, customer.getCity());
            pstmt.setString(6, customer.getPassword());
            pstmt.setBoolean(7, customer.isActive());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving customer: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get customer by email
     */
    public GymCustomer getCustomerByEmail(String email) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_CUSTOMER_BY_EMAIL)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCustomer(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting customer by email: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get customer by ID
     */
    public GymCustomer getCustomerById(String customerId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_CUSTOMER_BY_ID)) {
            
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCustomer(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting customer by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all customers
     */
    public Map<String, GymCustomer> getAllCustomers() {
        Map<String, GymCustomer> customers = new HashMap<>();
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_ALL_CUSTOMERS)) {
            
            while (rs.next()) {
                GymCustomer customer = mapResultSetToCustomer(rs);
                customers.put(customer.getEmail(), customer);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all customers: " + e.getMessage());
        }
        return customers;
    }
    
    /**
     * Update existing customer
     */
    public boolean updateCustomer(GymCustomer customer) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.UPDATE_CUSTOMER)) {
            
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhoneNumber());
            pstmt.setString(3, customer.getCity());
            pstmt.setString(4, customer.getPassword());
            pstmt.setBoolean(5, customer.isActive());
            pstmt.setString(6, customer.getEmail());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete customer by ID
     */
    public boolean deleteCustomer(String customerId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DELETE_CUSTOMER)) {
            
            pstmt.setString(1, customerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if email exists
     */
    public boolean emailExists(String email) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.COUNT_CUSTOMER_BY_EMAIL)) {
            
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
     * Map ResultSet to GymCustomer object
     */
    private GymCustomer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        GymCustomer customer = new GymCustomer();
        customer.setUserID(rs.getString("customer_id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhoneNumber(rs.getString("phone_number"));
        customer.setCity(rs.getString("city"));
        customer.setPassword(rs.getString("password"));
        
        Role role = new Role();
        role.setRoleName("CUSTOMER");
        customer.setRole(role);
        
        customer.setActive(rs.getBoolean("is_active"));
        
        return customer;
    }
}
