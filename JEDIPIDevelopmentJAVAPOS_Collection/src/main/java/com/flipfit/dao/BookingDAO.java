// TODO: Auto-generated Javadoc
package com.flipfit.dao;

import com.flipfit.bean.Booking;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class BookingDAO.
 *
 * @author team pi
 * @ClassName "BookingDAO"
 */
public class BookingDAO {

    /** The db manager. */
    private DatabaseConnection dbManager;

    /**
     * Instantiates a new booking DAO.
     */
    public BookingDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }

    /**
     * Generate booking id.
     *
     * @return the string
     */
    public synchronized String generateBookingId() {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement updateStmt = conn.prepareStatement(SQLConstants.UPDATE_COUNTER);
                PreparedStatement selectStmt = conn.prepareStatement(SQLConstants.SELECT_COUNTER)) {

            updateStmt.setString(1, "BOOKING");
            updateStmt.executeUpdate();

            selectStmt.setString(1, "BOOKING");
            ResultSet rs = selectStmt.executeQuery();

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
     * Save booking.
     *
     * @param booking the booking
     * @return true, if successful
     */
    public boolean saveBooking(Booking booking) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INSERT_BOOKING)) {

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
     * Gets the booking by id.
     *
     * @param bookingId the booking id
     * @return the booking by id
     */
    public Booking getBookingById(String bookingId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_BOOKING_BY_ID)) {

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
     * Gets the all bookings.
     *
     * @return the all bookings
     */
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_ALL_BOOKINGS)) {

            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all bookings: " + e.getMessage());
        }
        return bookings;
    }

    /**
     * Gets the bookings by user id.
     *
     * @param userId the user id
     * @return the bookings by user id
     */
    public List<Booking> getBookingsByUserId(String userId) {
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_BOOKINGS_BY_CUSTOMER_ID)) {

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
     * Gets the bookings by gym id.
     *
     * @param gymId the gym id
     * @return the bookings by gym id
     */
    public List<Booking> getBookingsByGymId(String gymId) {
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_BOOKINGS_BY_GYM_ID)) {

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
     * Gets the bookings by slot id.
     *
     * @param slotId the slot id
     * @return the bookings by slot id
     */
    public List<Booking> getBookingsBySlotId(String slotId) {
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_BOOKINGS_BY_SLOT_ID)) {

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
     * Gets the active bookings by user id.
     *
     * @param userId the user id
     * @return the active bookings by user id
     */
    public List<Booking> getActiveBookingsByUserId(String userId) {
        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_ACTIVE_BOOKINGS_BY_CUSTOMER_ID)) {

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
     * Update booking.
     *
     * @param booking the booking
     * @return true, if successful
     */
    public boolean updateBooking(Booking booking) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.UPDATE_BOOKING_STATUS)) {

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
     * Cancel booking.
     *
     * @param bookingId the booking id
     * @return true, if successful
     */
    public boolean cancelBooking(String bookingId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.CANCEL_BOOKING)) {

            pstmt.setString(1, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error cancelling booking: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete booking.
     *
     * @param bookingId the booking id
     * @return true, if successful
     */
    public boolean deleteBooking(String bookingId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DELETE_BOOKING)) {

            pstmt.setString(1, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
            return false;
        }
    }

    /**
     * Map result set to booking.
     *
     * @param rs the rs
     * @return the booking
     * @throws SQLException the SQL exception
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
