// TODO: Auto-generated Javadoc
package com.flipfit.dao;

import com.flipfit.bean.Role;
import com.flipfit.bean.User;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class UserDAO.
 *
 * @author team pi
 * @ClassName "UserDAO"
 */
public class UserDAO {

    /** The db manager. */
    private DatabaseConnection dbManager;

    /**
     * Instantiates a new user DAO.
     */
    public UserDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }

    /**
     * Generate user id.
     *
     * @return the string
     */
    public synchronized String generateUserId() {
        String sql = "UPDATE id_counters SET current_value = current_value + 1 WHERE counter_name = 'USER'";
        String selectSql = "SELECT current_value FROM id_counters WHERE counter_name = 'USER'";

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            ResultSet rs = stmt.executeQuery(selectSql);

            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "USR" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating user ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Save user.
     *
     * @param user the user
     * @return true, if successful
     */
    public boolean saveUser(User user) {
        String sql = "INSERT INTO users (user_id, name, email, phone_number, city, password, role, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setString(5, user.getCity());
            pstmt.setString(6, user.getPassword());
            pstmt.setString(7, user.getRole() != null ? user.getRole().getRoleName() : "CUSTOMER");
            pstmt.setBoolean(8, user.isActive());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the user by email.
     *
     * @param email the email
     * @return the user by email
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by email: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets the user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    public User getUserById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets the all users.
     *
     * @return the all users
     */
    public Map<String, User> getAllUsers() {
        Map<String, User> users = new HashMap<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                users.put(user.getEmail(), user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Update user.
     *
     * @param user the user
     * @return true, if successful
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, phone_number = ?, city = ?, password = ?, " +
                "role = ?, is_active = ? WHERE email = ?";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setString(3, user.getCity());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getRole() != null ? user.getRole().getRoleName() : "CUSTOMER");
            pstmt.setBoolean(6, user.isActive());
            pstmt.setString(7, user.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete user.
     *
     * @param userId the user id
     * @return true, if successful
     */
    public boolean deleteUser(String userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
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
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection conn = dbManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
     * Map result set to user.
     *
     * @param rs the rs
     * @return the user
     * @throws SQLException the SQL exception
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getString("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setCity(rs.getString("city"));
        user.setPassword(rs.getString("password"));

        Role role = new Role();
        role.setRoleName(rs.getString("role"));
        user.setRole(role);

        user.setActive(rs.getBoolean("is_active"));

        return user;
    }
}
