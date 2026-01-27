// TODO: Auto-generated Javadoc
package com.flipfit.dao;

import com.flipfit.bean.Payment;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class PaymentDAO.
 *
 * @author team pi
 * @ClassName "PaymentDAO"
 */
public class PaymentDAO {

    /** The db manager. */
    private DatabaseConnection dbManager;

    /**
     * Instantiates a new payment DAO.
     */
    public PaymentDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }

    /**
     * Generate payment id.
     *
     * @return the string
     */
    public synchronized String generatePaymentId() {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement updateStmt = conn.prepareStatement(SQLConstants.UPDATE_COUNTER);
                PreparedStatement selectStmt = conn.prepareStatement(SQLConstants.SELECT_COUNTER)) {

            updateStmt.setString(1, "PAYMENT");
            updateStmt.executeUpdate();

            selectStmt.setString(1, "PAYMENT");
            ResultSet rs = selectStmt.executeQuery();

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
     * Save payment.
     *
     * @param payment the payment
     * @return true, if successful
     */
    public boolean savePayment(Payment payment) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INSERT_PAYMENT)) {

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
     * Gets the payment by id.
     *
     * @param paymentId the payment id
     * @return the payment by id
     */
    public Payment getPaymentById(String paymentId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_PAYMENT_BY_ID)) {

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
     * Gets the payment by booking id.
     *
     * @param bookingId the booking id
     * @return the payment by booking id
     */
    public Payment getPaymentByBookingId(String bookingId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_PAYMENT_BY_BOOKING_ID)) {

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
     * Gets the all payments.
     *
     * @return the all payments
     */
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_ALL_PAYMENTS)) {

            while (rs.next()) {
                payments.add(mapResultSetToPayment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all payments: " + e.getMessage());
        }
        return payments;
    }

    /**
     * Gets the payments by user id.
     *
     * @param userId the user id
     * @return the payments by user id
     */
    public List<Payment> getPaymentsByUserId(String userId) {
        List<Payment> payments = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_PAYMENTS_BY_CUSTOMER_ID)) {

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
     * Gets the successful payments by user id.
     *
     * @param userId the user id
     * @return the successful payments by user id
     */
    public List<Payment> getSuccessfulPaymentsByUserId(String userId) {
        List<Payment> payments = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn
                        .prepareStatement(SQLConstants.SELECT_SUCCESSFUL_PAYMENTS_BY_CUSTOMER_ID)) {

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
     * Update payment status.
     *
     * @param paymentId the payment id
     * @param status    the status
     * @return true, if successful
     */
    public boolean updatePaymentStatus(String paymentId, String status) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.UPDATE_PAYMENT_STATUS)) {

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
     * Delete payment.
     *
     * @param paymentId the payment id
     * @return true, if successful
     */
    public boolean deletePayment(String paymentId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DELETE_PAYMENT)) {

            pstmt.setString(1, paymentId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting payment: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the total payment by user id.
     *
     * @param userId the user id
     * @return the total payment by user id
     */
    public BigDecimal getTotalPaymentByUserId(String userId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.CALCULATE_TOTAL_REVENUE_BY_CUSTOMER)) {

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
     * Map result set to payment.
     *
     * @param rs the rs
     * @return the payment
     * @throws SQLException the SQL exception
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
