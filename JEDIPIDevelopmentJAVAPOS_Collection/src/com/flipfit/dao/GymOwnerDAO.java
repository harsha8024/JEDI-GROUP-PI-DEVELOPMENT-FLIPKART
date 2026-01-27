// TODO: Auto-generated Javadoc
package com.flipfit.dao;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Role;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class GymOwnerDAO.
 *
 * @author team pi
 * @ClassName "GymOwnerDAO"
 */
public class GymOwnerDAO {

    /** The db manager. */
    private DatabaseConnection dbManager;

    /**
     * Instantiates a new gym owner DAO.
     */
    public GymOwnerDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }

    /**
     * Generate owner id.
     *
     * @return the string
     */
    public synchronized String generateOwnerId() {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement updateStmt = conn.prepareStatement(SQLConstants.UPDATE_COUNTER);
                PreparedStatement selectStmt = conn.prepareStatement(SQLConstants.SELECT_COUNTER)) {

            updateStmt.setString(1, "OWNER");
            updateStmt.executeUpdate();

            selectStmt.setString(1, "OWNER");
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "OWN" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating owner ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Save gym owner.
     *
     * @param owner the owner
     * @return true, if successful
     */
    public boolean saveGymOwner(GymOwner owner) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INSERT_GYM_OWNER)) {

            pstmt.setString(1, owner.getUserID());
            pstmt.setString(2, owner.getName());
            pstmt.setString(3, owner.getEmail());
            pstmt.setString(4, owner.getPhoneNumber());
            pstmt.setString(5, owner.getCity());
            pstmt.setString(6, owner.getPassword());
            pstmt.setString(7, owner.getPanNumber());
            pstmt.setString(8, owner.getAadharNumber());
            pstmt.setBoolean(9, owner.isActive());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error saving gym owner: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the gym owner by email.
     *
     * @param email the email
     * @return the gym owner by email
     */
    public GymOwner getGymOwnerByEmail(String email) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_GYM_OWNER_BY_EMAIL)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToGymOwner(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting gym owner by email: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets the gym owner by id.
     *
     * @param ownerId the owner id
     * @return the gym owner by id
     */
    public GymOwner getGymOwnerById(String ownerId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_GYM_OWNER_BY_ID)) {

            pstmt.setString(1, ownerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToGymOwner(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting gym owner by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets the all gym owners.
     *
     * @return the all gym owners
     */
    public Map<String, GymOwner> getAllGymOwners() {
        Map<String, GymOwner> owners = new HashMap<>();

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_ALL_GYM_OWNERS)) {

            while (rs.next()) {
                GymOwner owner = mapResultSetToGymOwner(rs);
                owners.put(owner.getEmail(), owner);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all gym owners: " + e.getMessage());
        }
        return owners;
    }

    /**
     * Update gym owner.
     *
     * @param owner the owner
     * @return true, if successful
     */
    public boolean updateGymOwner(GymOwner owner) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.UPDATE_GYM_OWNER)) {

            pstmt.setString(1, owner.getName());
            pstmt.setString(2, owner.getPhoneNumber());
            pstmt.setString(3, owner.getCity());
            pstmt.setString(4, owner.getPassword());
            pstmt.setString(5, owner.getPanNumber());
            pstmt.setString(6, owner.getAadharNumber());
            pstmt.setBoolean(7, owner.isActive());
            pstmt.setString(8, owner.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating gym owner: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete gym owner.
     *
     * @param ownerId the owner id
     * @return true, if successful
     */
    public boolean deleteGymOwner(String ownerId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DELETE_GYM_OWNER)) {

            pstmt.setString(1, ownerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting gym owner: " + e.getMessage());
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
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.COUNT_GYM_OWNER_BY_EMAIL)) {

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
     * Map result set to gym owner.
     *
     * @param rs the rs
     * @return the gym owner
     * @throws SQLException the SQL exception
     */
    private GymOwner mapResultSetToGymOwner(ResultSet rs) throws SQLException {
        GymOwner owner = new GymOwner();
        owner.setUserID(rs.getString("owner_id"));
        owner.setName(rs.getString("name"));
        owner.setEmail(rs.getString("email"));
        owner.setPhoneNumber(rs.getString("phone_number"));
        owner.setCity(rs.getString("city"));
        owner.setPassword(rs.getString("password"));
        owner.setPanNumber(rs.getString("pan_number"));
        owner.setAadharNumber(rs.getString("aadhar_number"));

        Role role = new Role();
        role.setRoleName("OWNER");
        owner.setRole(role);

        owner.setActive(rs.getBoolean("is_active"));

        return owner;
    }
}
