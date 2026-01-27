package com.flipfit.dao;

import com.flipfit.bean.Slot;
import com.flipfit.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalTime;
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
        String sql = "UPDATE id_counters SET current_value = current_value + 1 WHERE counter_name = 'SLOT'";
        String selectSql = "SELECT current_value FROM id_counters WHERE counter_name = 'SLOT'";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery(selectSql);
            
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
        String sql = "INSERT INTO slots (slot_id, gym_id, start_time, end_time, capacity, available_seats) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
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
        String sql = "SELECT * FROM slots WHERE slot_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
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
        String sql = "SELECT * FROM slots ORDER BY start_time";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
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
        String sql = "SELECT * FROM slots WHERE gym_id = ? ORDER BY start_time";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
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
        String sql = "SELECT * FROM slots WHERE gym_id = ? AND available_seats > 0 ORDER BY start_time";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
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
        String sql = "UPDATE slots SET start_time = ?, end_time = ?, capacity = ?, available_seats = ? " +
                     "WHERE slot_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
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
        String sql = "UPDATE slots SET available_seats = available_seats - 1 " +
                     "WHERE slot_id = ? AND available_seats > 0";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
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
        String sql = "UPDATE slots SET available_seats = available_seats + 1 " +
                     "WHERE slot_id = ? AND available_seats < capacity";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
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
        String sql = "DELETE FROM slots WHERE slot_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
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
