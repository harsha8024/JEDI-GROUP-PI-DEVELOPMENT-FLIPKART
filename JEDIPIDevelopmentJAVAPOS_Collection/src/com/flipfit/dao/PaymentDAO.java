package com.flipfit.dao;

import com.flipfit.bean.Payment;
import com.flipfit.database.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Payment entity
 * Handles all database operations related to payments
 */
public class PaymentDAO {
    
    private DatabaseConnection dbManager;
    
    public PaymentDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }
    
    /**
     * Generate new payment ID from database counter
     */
    public synchronized String generatePaymentId() {
        String sql = "UPDATE id_counters SET current_value = current_value + 1 WHERE counter_name = 'PAYMENT'";
        String selectSql = "SELECT current_value FROM id_counters WHERE counter_name = 'PAYMENT'";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery(selectSql);
            
            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "PAY" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating payment ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Save a new payment to database
     */
    public boolean savePayment(Payment payment) {
        String sql = "INSERT INTO payments (payment_id, booking_id, customer_id, amount, payment_method, payment_status, payment_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, payment.getPaymentId());
            pstmt.setString(2, payment.getBookingId());
            pstmt.setString(3, payment.getUserId());
            pstmt.setBigDecimal(4, payment.getAmount());
            pstmt.setString(5, payment.getPaymentMethod());
            pstmt.setString(6, payment.getPaymentStatus());
            pstmt.setTimestamp(7, Timestamp.valueOf(payment.getPaymentDate()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving payment: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get payment by ID
     */
    public Payment getPaymentById(String paymentId) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, paymentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPayment(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting payment by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get payment by booking ID
     */
    public Payment getPaymentByBookingId(String bookingId) {
        String sql = "SELECT * FROM payments WHERE booking_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPayment(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting payment by booking ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all payments
     */
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY payment_date DESC";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all payments: " + e.getMessage());
        }
        return payments;
    }
    
    /**
     * Get payments by user ID (customer ID)
     */
    public List<Payment> getPaymentsByUserId(String userId) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE customer_id = ? ORDER BY payment_date DESC";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting payments by user ID: " + e.getMessage());
        }
        return payments;
    }
    
    /**
     * Get successful payments by user ID (customer ID)
     */
    public List<Payment> getSuccessfulPaymentsByUserId(String userId) {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE customer_id = ? AND payment_status = 'SUCCESS' ORDER BY payment_date DESC";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting successful payments: " + e.getMessage());
        }
        return payments;
    }
    
    /**
     * Update payment status
     */
    public boolean updatePaymentStatus(String paymentId, String status) {
        String sql = "UPDATE payments SET payment_status = ? WHERE payment_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setString(2, paymentId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete payment by ID
     */
    public boolean deletePayment(String paymentId) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, paymentId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting payment: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get total payment amount by user ID (customer ID)
     */
    public BigDecimal getTotalPaymentByUserId(String userId) {
        String sql = "SELECT SUM(amount) as total FROM payments WHERE customer_id = ? AND payment_status = 'SUCCESS'";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total");
                return total != null ? total : BigDecimal.ZERO;
            }
        } catch (SQLException e) {
            System.err.println("Error getting total payment: " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }
    
    /**
     * Map ResultSet to Payment object
     */
    private Payment mapResultSetToPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(rs.getString("payment_id"));
        payment.setBookingId(rs.getString("booking_id"));
        payment.setUserId(rs.getString("customer_id"));
        payment.setAmount(rs.getBigDecimal("amount"));
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setPaymentStatus(rs.getString("payment_status"));
        payment.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
        return payment;
    }
}
