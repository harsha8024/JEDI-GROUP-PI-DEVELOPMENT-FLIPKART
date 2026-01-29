// TODO: Auto-generated Javadoc
package com.flipfit.dao;

import com.flipfit.bean.GymAdmin;
import com.flipfit.bean.Role;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class AdminDAO.
 *
 * @author team pi
 * @ClassName "AdminDAO"
 */
public class AdminDAO {

    /** The db manager. */
    private DatabaseConnection dbManager;

    /**
     * Instantiates a new admin DAO.
     */
    public AdminDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }

    /**
     * Generate admin id.
     *
     * @return the string
     */
    public synchronized String generateAdminId() {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement updateStmt = conn.prepareStatement(SQLConstants.UPDATE_COUNTER);
                PreparedStatement selectStmt = conn.prepareStatement(SQLConstants.SELECT_COUNTER)) {

            updateStmt.setString(1, "ADMIN");
            updateStmt.executeUpdate();

            selectStmt.setString(1, "ADMIN");
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "ADM" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating admin ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Save admin.
     *
     * @param admin the admin
     * @return true, if successful
     */
    public boolean saveAdmin(GymAdmin admin) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INSERT_ADMIN)) {

            pstmt.setString(1, admin.getUserID());
            pstmt.setString(2, admin.getName());
            pstmt.setString(3, admin.getEmail());
            pstmt.setString(4, admin.getPhoneNumber());
            pstmt.setString(5, admin.getCity());
            pstmt.setString(6, admin.getPassword());
            pstmt.setBoolean(7, true); // Admins are always active

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error saving admin: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the admin by email.
     *
     * @param email the email
     * @return the admin by email
     */
    public GymAdmin getAdminByEmail(String email) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_ADMIN_BY_EMAIL)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAdmin(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting admin by email: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets the admin by id.
     *
     * @param adminId the admin id
     * @return the admin by id
     */
    public GymAdmin getAdminById(String adminId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_ADMIN_BY_ID)) {

            pstmt.setString(1, adminId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToAdmin(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting admin by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets the all admins.
     *
     * @return the all admins
     */
    public Map<String, GymAdmin> getAllAdmins() {
        Map<String, GymAdmin> admins = new HashMap<>();

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_ALL_ADMINS)) {

            while (rs.next()) {
                GymAdmin admin = mapResultSetToAdmin(rs);
                admins.put(admin.getEmail(), admin);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all admins: " + e.getMessage());
        }
        return admins;
    }

    /**
     * Update admin.
     *
     * @param admin the admin
     * @return true, if successful
     */
    public boolean updateAdmin(GymAdmin admin) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.UPDATE_ADMIN)) {

            pstmt.setString(1, admin.getName());
            pstmt.setString(2, admin.getPhoneNumber());
            pstmt.setString(3, admin.getCity());
            pstmt.setString(4, admin.getPassword());
            pstmt.setBoolean(5, true); // Admins are always active
            pstmt.setString(6, admin.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating admin: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete admin.
     *
     * @param adminId the admin id
     * @return true, if successful
     */
    public boolean deleteAdmin(String adminId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DELETE_ADMIN)) {

            pstmt.setString(1, adminId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
            return false;
        }
    }

    /**
     * Email exists.
     *
     * @param email the email
     * @return true, if successful
     */
    public boolean emailExists(String email) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.COUNT_ADMIN_BY_EMAIL)) {

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
     * Approve gym.
     *
     * @param gymId the gym id
     * @return true, if successful
     */
    public boolean approveGym(String gymId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.APPROVE_GYM)) {

            pstmt.setString(1, gymId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error approving gym: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reject gym.
     *
     * @param gymId the gym id
     * @return true, if successful
     */
    public boolean rejectGym(String gymId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.REJECT_GYM)) {

            pstmt.setString(1, gymId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error rejecting gym: " + e.getMessage());
            return false;
        }
    }

    /**
     * Approve gym owner.
     *
     * @param ownerId the owner id
     * @return true, if successful
     */
    public boolean approveGymOwner(String ownerId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.ACTIVATE_GYM_OWNER)) {

            pstmt.setString(1, ownerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error approving gym owner: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deactivate gym owner.
     *
     * @param ownerId the owner id
     * @return true, if successful
     */
    public boolean deactivateGymOwner(String ownerId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DEACTIVATE_GYM_OWNER)) {

            pstmt.setString(1, ownerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deactivating gym owner: " + e.getMessage());
            return false;
        }
    }

    /**
     * Approve customer.
     *
     * @param customerId the customer id
     * @return true, if successful
     */
    public boolean approveCustomer(String customerId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.ACTIVATE_CUSTOMER)) {

            pstmt.setString(1, customerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error approving customer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deactivate customer.
     *
     * @param customerId the customer id
     * @return true, if successful
     */
    public boolean deactivateCustomer(String customerId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DEACTIVATE_CUSTOMER)) {

            pstmt.setString(1, customerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deactivating customer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the user counts.
     *
     * @return the user counts
     */
    public Map<String, Integer> getUserCounts() {
        Map<String, Integer> counts = new HashMap<>();

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement()) {

            // Count customers
            ResultSet rs = stmt.executeQuery(SQLConstants.COUNT_ALL_CUSTOMERS);
            if (rs.next()) {
                counts.put("customers", rs.getInt("count"));
            }

            // Count gym owners
            rs = stmt.executeQuery(SQLConstants.COUNT_ALL_GYM_OWNERS);
            if (rs.next()) {
                counts.put("gym_owners", rs.getInt("count"));
            }

            // Count admins
            rs = stmt.executeQuery(SQLConstants.COUNT_ALL_ADMINS);
            if (rs.next()) {
                counts.put("admins", rs.getInt("count"));
            }

            // Total count
            int total = counts.getOrDefault("customers", 0) +
                    counts.getOrDefault("gym_owners", 0) +
                    counts.getOrDefault("admins", 0);
            counts.put("total", total);

        } catch (SQLException e) {
            System.err.println("Error getting user counts: " + e.getMessage());
        }

        return counts;
    }

    /**
     * Gets the pending gyms count.
     *
     * @return the pending gyms count
     */
    public int getPendingGymsCount() {
        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.COUNT_PENDING_GYMS)) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error getting pending gyms count: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Gets the inactive gym owners count.
     *
     * @return the inactive gym owners count
     */
    public int getInactiveGymOwnersCount() {
        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.COUNT_INACTIVE_GYM_OWNERS)) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error getting inactive gym owners count: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Map result set to admin.
     *
     * @param rs the rs
     * @return the gym admin
     * @throws SQLException the SQL exception
     */
    private GymAdmin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        GymAdmin admin = new GymAdmin();
        admin.setUserID(rs.getString("admin_id"));
        admin.setName(rs.getString("name"));
        admin.setEmail(rs.getString("email"));
        admin.setPhoneNumber(rs.getString("phone_number"));
        admin.setCity(rs.getString("city"));
        admin.setPassword(rs.getString("password"));

        Role role = new Role();
        role.setRoleName("ADMIN");
        admin.setRole(role);

        admin.setActive(true); // Admins are always active

        return admin;
    }

    /**
     * Approve slot.
     *
     * @param slotId the slot id
     * @return true, if successful
     */
    public boolean approveSlot(String slotId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.APPROVE_SLOT)) {

            pstmt.setString(1, slotId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error approving slot: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reject slot.
     *
     * @param slotId the slot id
     * @return true, if successful
     */
    public boolean rejectSlot(String slotId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.REJECT_SLOT)) {

            pstmt.setString(1, slotId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error rejecting slot: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the pending slots count.
     *
     * @return the pending slots count
     */
    public int getPendingSlotsCount() {
        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.COUNT_PENDING_SLOTS)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting pending slots count: " + e.getMessage());
        }
        return 0;
    }
}
