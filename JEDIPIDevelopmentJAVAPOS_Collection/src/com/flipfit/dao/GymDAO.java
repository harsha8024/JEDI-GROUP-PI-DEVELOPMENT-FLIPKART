// TODO: Auto-generated Javadoc
package com.flipfit.dao;

import com.flipfit.bean.Gym;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class GymDAO.
 *
 * @author team pi
 * @ClassName "GymDAO"
 */
public class GymDAO {

    /** The db manager. */
    private DatabaseConnection dbManager;

    /**
     * Instantiates a new gym DAO.
     */
    public GymDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }

    /**
     * Generate gym id.
     *
     * @return the string
     */
    public synchronized String generateGymId() {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement updateStmt = conn.prepareStatement(SQLConstants.UPDATE_COUNTER);
                PreparedStatement selectStmt = conn.prepareStatement(SQLConstants.SELECT_COUNTER)) {

            updateStmt.setString(1, "GYM");
            updateStmt.executeUpdate();

            selectStmt.setString(1, "GYM");
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "GYM" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating gym ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Save gym.
     *
     * @param gym the gym
     * @return true, if successful
     */
    public boolean saveGym(Gym gym) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INSERT_GYM)) {

            pstmt.setString(1, gym.getGymId());
            pstmt.setString(2, gym.getGymName());
            pstmt.setString(3, gym.getLocation());
            pstmt.setString(4, gym.getGymOwnerId());
            pstmt.setBoolean(5, gym.isApproved());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error saving gym: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the gym by id.
     *
     * @param gymId the gym id
     * @return the gym by id
     */
    public Gym getGymById(String gymId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_GYM_BY_ID)) {

            pstmt.setString(1, gymId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToGym(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting gym by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets the all gyms.
     *
     * @return the all gyms
     */
    public List<Gym> getAllGyms() {
        List<Gym> gyms = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_ALL_GYMS)) {

            while (rs.next()) {
                gyms.add(mapResultSetToGym(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all gyms: " + e.getMessage());
        }
        return gyms;
    }

    /**
     * Gets the gyms by owner id.
     *
     * @param ownerId the owner id
     * @return the gyms by owner id
     */
    public List<Gym> getGymsByOwnerId(String ownerId) {
        List<Gym> gyms = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_GYMS_BY_OWNER_ID)) {

            pstmt.setString(1, ownerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                gyms.add(mapResultSetToGym(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting gyms by owner ID: " + e.getMessage());
        }
        return gyms;
    }

    /**
     * Gets the gyms by location.
     *
     * @param location the location
     * @return the gyms by location
     */
    public List<Gym> getGymsByLocation(String location) {
        List<Gym> gyms = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_GYMS_BY_LOCATION)) {

            pstmt.setString(1, location);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                gyms.add(mapResultSetToGym(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting gyms by location: " + e.getMessage());
        }
        return gyms;
    }

    /**
     * Gets the approved gyms.
     *
     * @return the approved gyms
     */
    public List<Gym> getApprovedGyms() {
        List<Gym> gyms = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_APPROVED_GYMS)) {

            while (rs.next()) {
                gyms.add(mapResultSetToGym(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting approved gyms: " + e.getMessage());
        }
        return gyms;
    }

    /**
     * Gets the pending gyms.
     *
     * @return the pending gyms
     */
    public List<Gym> getPendingGyms() {
        List<Gym> gyms = new ArrayList<>();

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_PENDING_GYMS)) {

            while (rs.next()) {
                gyms.add(mapResultSetToGym(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting pending gyms: " + e.getMessage());
        }
        return gyms;
    }

    /**
     * Update gym.
     *
     * @param gym the gym
     * @return true, if successful
     */
    public boolean updateGym(Gym gym) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.UPDATE_GYM)) {

            pstmt.setString(1, gym.getGymName());
            pstmt.setString(2, gym.getLocation());
            pstmt.setBoolean(3, gym.isApproved());
            pstmt.setString(4, gym.getGymId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating gym: " + e.getMessage());
            return false;
        }
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
     * Delete gym.
     *
     * @param gymId the gym id
     * @return true, if successful
     */
    public boolean deleteGym(String gymId) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DELETE_GYM)) {

            pstmt.setString(1, gymId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting gym: " + e.getMessage());
            return false;
        }
    }

    /**
     * Map result set to gym.
     *
     * @param rs the rs
     * @return the gym
     * @throws SQLException the SQL exception
     */
    private Gym mapResultSetToGym(ResultSet rs) throws SQLException {
        Gym gym = new Gym();
        gym.setGymId(rs.getString("gym_id"));
        gym.setGymName(rs.getString("gym_name"));
        gym.setLocation(rs.getString("location"));
        gym.setGymOwnerId(rs.getString("gym_owner_id"));
        gym.setApproved(rs.getBoolean("is_approved"));
        return gym;
    }
}
