// TODO: Auto-generated Javadoc
package com.flipfit.dao;

import com.flipfit.bean.Slot;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class SlotDAO.
 *
 * @author team pi
 * @ClassName "SlotDAO"
 */
public class SlotDAO {

    /** The db manager. */
    private DatabaseConnection dbManager;

    /**
     * Instantiates a new slot DAO.
     */
    public SlotDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }

    /**
     * Generate slot id.
     *
     * @return the string
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
     * Save slot.
     *
     * @param slot the slot
     * @return true, if successful
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
            pstmt.setBoolean(7, slot.isApproved());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error saving slot: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the slot by id.
     *
     * @param slotId the slot id
     * @return the slot by id
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
     * Gets the all slots.
     *
     * @return the all slots
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
     * Gets the slots by gym id.
     *
     * @param gymId the gym id
     * @return the slots by gym id
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
     * Gets the available slots by gym id.
     *
     * @param gymId the gym id
     * @return the available slots by gym id
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
     * Update slot.
     *
     * @param slot the slot
     * @return true, if successful
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
     * Decrease available seats.
     *
     * @param slotId the slot id
     * @return true, if successful
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
     * Increase available seats.
     *
     * @param slotId the slot id
     * @return true, if successful
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
     * Delete slot.
     *
     * @param slotId the slot id
     * @return true, if successful
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
     * Map result set to slot.
     *
     * @param rs the rs
     * @return the slot
     * @throws SQLException the SQL exception
     */
    private Slot mapResultSetToSlot(ResultSet rs) throws SQLException {
        Slot slot = new Slot();
        slot.setSlotId(rs.getString("slot_id"));
        slot.setGymId(rs.getString("gym_id"));
        slot.setStartTime(rs.getTime("start_time").toLocalTime());
        slot.setEndTime(rs.getTime("end_time").toLocalTime());
        slot.setCapacity(rs.getInt("capacity"));
        slot.setAvailableSeats(rs.getInt("available_seats"));
        slot.setApproved(rs.getBoolean("is_approved"));
        return slot;
    }

    /**
     * Gets the pending slots.
     *
     * @return the pending slots
     */
    public List<Slot> getPendingSlots() {
        List<Slot> slots = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_PENDING_SLOTS)) {

            while (rs.next()) {
                slots.add(mapResultSetToSlot(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting pending slots: " + e.getMessage());
        }
        return slots;
    }
}
