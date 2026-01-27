package com.flipfit.dao;

import com.flipfit.bean.Slot;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Slot entity
 * Handles all database operations related to slots
 */
public class SlotDAO {
    
    private DatabaseConnection dbManager;
    
    public SlotDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }
    
    /**
     * Generate new slot ID from database counter
     */
    public synchronized String generateSlotId() {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(SQLConstants.UPDATE_COUNTER);
             PreparedStatement selectStmt = conn.prepareStatement(SQLConstants.SELECT_COUNTER)) {
            
            updateStmt.setString(1, "SLOT");
            updateStmt.executeUpdate();
            
            selectStmt.setString(1, "SLOT");
            ResultSet rs = selectStmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "SLT" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating slot ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Save a new slot to database
     */
    public boolean saveSlot(Slot slot) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INSERT_SLOT)) {
            
            pstmt.setString(1, slot.getSlotId());
            pstmt.setString(2, slot.getGymId());
            pstmt.setTime(3, Time.valueOf(slot.getStartTime()));
            pstmt.setTime(4, Time.valueOf(slot.getEndTime()));
            pstmt.setInt(5, slot.getCapacity());
            pstmt.setInt(6, slot.getAvailableSeats());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving slot: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get slot by ID
     */
    public Slot getSlotById(String slotId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_SLOT_BY_ID)) {
            
            pstmt.setString(1, slotId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToSlot(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting slot by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all slots
     */
    public List<Slot> getAllSlots() {
        List<Slot> slots = new ArrayList<>();
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_ALL_SLOTS)) {
            
            while (rs.next()) {
                slots.add(mapResultSetToSlot(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all slots: " + e.getMessage());
        }
        return slots;
    }
    
    /**
     * Get slots by gym ID
     */
    public List<Slot> getSlotsByGymId(String gymId) {
        List<Slot> slots = new ArrayList<>();
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_SLOTS_BY_GYM_ID)) {
            
            pstmt.setString(1, gymId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                slots.add(mapResultSetToSlot(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting slots by gym ID: " + e.getMessage());
        }
        return slots;
    }
    
    /**
     * Get available slots by gym ID
     */
    public List<Slot> getAvailableSlotsByGymId(String gymId) {
        List<Slot> slots = new ArrayList<>();
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_AVAILABLE_SLOTS_BY_GYM_ID)) {
            
            pstmt.setString(1, gymId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                slots.add(mapResultSetToSlot(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting available slots: " + e.getMessage());
        }
        return slots;
    }
    
    /**
     * Update existing slot
     */
    public boolean updateSlot(Slot slot) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.UPDATE_SLOT)) {
            
            pstmt.setTime(1, Time.valueOf(slot.getStartTime()));
            pstmt.setTime(2, Time.valueOf(slot.getEndTime()));
            pstmt.setInt(3, slot.getCapacity());
            pstmt.setInt(4, slot.getAvailableSeats());
            pstmt.setString(5, slot.getSlotId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating slot: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Decrease available seats (for booking)
     */
    public boolean decreaseAvailableSeats(String slotId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DECREASE_AVAILABLE_SEATS)) {
            
            pstmt.setString(1, slotId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error decreasing available seats: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Increase available seats (for cancellation)
     */
    public boolean increaseAvailableSeats(String slotId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INCREASE_AVAILABLE_SEATS)) {
            
            pstmt.setString(1, slotId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error increasing available seats: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete slot by ID
     */
    public boolean deleteSlot(String slotId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DELETE_SLOT)) {
            
            pstmt.setString(1, slotId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting slot: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Map ResultSet to Slot object
     */
    private Slot mapResultSetToSlot(ResultSet rs) throws SQLException {
        Slot slot = new Slot();
        slot.setSlotId(rs.getString("slot_id"));
        slot.setGymId(rs.getString("gym_id"));
        slot.setStartTime(rs.getTime("start_time").toLocalTime());
        slot.setEndTime(rs.getTime("end_time").toLocalTime());
        slot.setCapacity(rs.getInt("capacity"));
        slot.setAvailableSeats(rs.getInt("available_seats"));
        return slot;
    }
}
