package com.flipfit.dao;

import com.flipfit.bean.Registration;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    private DatabaseConnection dbManager;

    // Define SQL Queries directly here for simplicity
    private static final String INSERT_REGISTRATION = 
        "INSERT INTO registrations (registration_id, user_id, user_role, status, registration_date) VALUES (?, ?, ?, ?, ?)";
    
    private static final String SELECT_PENDING_REGISTRATIONS = 
        "SELECT * FROM registrations WHERE status = 'PENDING'";
    
    private static final String UPDATE_REGISTRATION_STATUS = 
        "UPDATE registrations SET status = ?, approved_by = ?, approval_date = ? WHERE registration_id = ?";
    
    private static final String SELECT_REGISTRATION_BY_ID = 
        "SELECT * FROM registrations WHERE registration_id = ?";

    public RegistrationDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }

    /**
     * Creates a new registration request in the database
     */
    public boolean saveRegistration(Registration registration) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_REGISTRATION)) {
            
            pstmt.setString(1, registration.getRegistrationId());
            pstmt.setString(2, registration.getUserId());
            pstmt.setString(3, registration.getRoleId());
            pstmt.setString(4, registration.getStatus());
            pstmt.setTimestamp(5, Timestamp.valueOf(registration.getRegistrationDate()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving registration: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all registrations with status 'PENDING'
     */
    public List<Registration> getPendingRegistrations() {
        List<Registration> pendingList = new ArrayList<>();
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_PENDING_REGISTRATIONS)) {
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Registration reg = mapResultSetToRegistration(rs);
                pendingList.add(reg);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching pending registrations: " + e.getMessage());
        }
        return pendingList;
    }

    /**
     * Updates the status of a registration (APPROVED/REJECTED)
     */
    public boolean updateRegistrationStatus(String registrationId, String status, String adminId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_REGISTRATION_STATUS)) {
            
            pstmt.setString(1, status);
            pstmt.setString(2, adminId);
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(4, registrationId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating registration status: " + e.getMessage());
            return false;
        }
    }

    /**
     * Helper to retrieve a single registration by ID
     */
    public Registration getRegistrationById(String registrationId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_REGISTRATION_BY_ID)) {
            
            pstmt.setString(1, registrationId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToRegistration(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding registration: " + e.getMessage());
        }
        return null;
    }

    /**
     * Maps database result set to Registration object
     */
    private Registration mapResultSetToRegistration(ResultSet rs) throws SQLException {
        Registration reg = new Registration();
        reg.setRegistrationId(rs.getString("registration_id"));
        reg.setUserId(rs.getString("user_id"));
        reg.setRoleId(rs.getString("user_role"));
        reg.setStatus(rs.getString("status"));
        
        Timestamp regDate = rs.getTimestamp("registration_date");
        if (regDate != null) reg.setRegistrationDate(regDate.toLocalDateTime());
        
        reg.setApprovedBy(rs.getString("approved_by"));
        
        Timestamp appDate = rs.getTimestamp("approval_date");
        if (appDate != null) reg.setApprovalDate(appDate.toLocalDateTime());
        
        return reg;
    }
}