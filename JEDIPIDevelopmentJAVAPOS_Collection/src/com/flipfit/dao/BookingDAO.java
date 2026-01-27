package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Booking entity
 * Handles all database operations related to bookings
 */
public class BookingDAO {
    
    private DatabaseConnection dbManager;
    
    public BookingDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }
    
    /**
     * Generate new booking ID from database counter
     */
    public synchronized String generateBookingId() {
        String sql = "UPDATE id_counters SET current_value = current_value + 1 WHERE counter_name = 'BOOKING'";
        String selectSql = "SELECT current_value FROM id_counters WHERE counter_name = 'BOOKING'";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery(selectSql);
            
            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "BKG" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating booking ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Save a new booking to database
     */
    public boolean saveBooking(Booking booking) {
        String sql = "INSERT INTO bookings (booking_id, customer_id, slot_id, gym_id, booking_date, status, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, booking.getBookingId());
            pstmt.setString(2, booking.getUserId());
            pstmt.setString(3, booking.getSlotId());
            pstmt.setString(4, booking.getGymId());
            pstmt.setDate(5, Date.valueOf(booking.getBookingDate()));
            pstmt.setString(6, booking.getStatus());
            pstmt.setTimestamp(7, Timestamp.valueOf(booking.getCreatedAt()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving booking: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get booking by ID
     */
    public Booking getBookingById(String bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToBooking(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting booking by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all bookings
     */
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY created_at DESC";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all bookings: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Get bookings by user ID (customer ID)
     */
    public List<Booking> getBookingsByUserId(String userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE customer_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting bookings by user ID: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Get bookings by gym ID
     */
    public List<Booking> getBookingsByGymId(String gymId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE gym_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, gymId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting bookings by gym ID: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Get bookings by slot ID
     */
    public List<Booking> getBookingsBySlotId(String slotId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE slot_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, slotId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting bookings by slot ID: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Get active bookings by user ID (customer ID)
     */
    public List<Booking> getActiveBookingsByUserId(String userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE customer_id = ? AND status = 'CONFIRMED' ORDER BY created_at DESC";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting active bookings: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Update existing booking
     */
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, booking.getStatus());
            pstmt.setString(2, booking.getBookingId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating booking: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cancel booking
     */
    public boolean cancelBooking(String bookingId) {
        String sql = "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error cancelling booking: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete booking by ID
     */
    public boolean deleteBooking(String bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Map ResultSet to Booking object
     */
    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getString("booking_id"));
        booking.setUserId(rs.getString("customer_id"));
        booking.setSlotId(rs.getString("slot_id"));
        booking.setGymId(rs.getString("gym_id"));
        booking.setBookingDate(rs.getDate("booking_date").toLocalDate());
        booking.setStatus(rs.getString("status"));
        booking.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return booking;
    }
}
