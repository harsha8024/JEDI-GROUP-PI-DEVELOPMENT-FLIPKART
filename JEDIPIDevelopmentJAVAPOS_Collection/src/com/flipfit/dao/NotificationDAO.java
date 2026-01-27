package com.flipfit.dao;

import com.flipfit.bean.Notification;
import com.flipfit.constants.SQLConstants;
import com.flipfit.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Notification entity
 * Handles all database operations related to notifications
 */
public class NotificationDAO {
    
    private DatabaseConnection dbManager;
    
    public NotificationDAO() {
        this.dbManager = DatabaseConnection.getInstance();
    }
    
    /**
     * Generate new notification ID from database counter
     */
    public synchronized String generateNotificationId() {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(SQLConstants.UPDATE_COUNTER);
             PreparedStatement selectStmt = conn.prepareStatement(SQLConstants.SELECT_COUNTER)) {
            
            updateStmt.setString(1, "NOTIFICATION");
            updateStmt.executeUpdate();
            
            selectStmt.setString(1, "NOTIFICATION");
            ResultSet rs = selectStmt.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("current_value");
                return "NOT" + id;
            }
        } catch (SQLException e) {
            System.err.println("Error generating notification ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Save a new notification to database
     */
    public boolean saveNotification(Notification notification) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.INSERT_NOTIFICATION)) {
            
            pstmt.setString(1, notification.getNotificationId());
            pstmt.setString(2, notification.getUserId());
            pstmt.setString(3, notification.getUserType() != null ? notification.getUserType() : "CUSTOMER");
            pstmt.setString(4, notification.getTitle());
            pstmt.setString(5, notification.getMessage());
            pstmt.setBoolean(6, notification.isRead());
            pstmt.setTimestamp(7, Timestamp.valueOf(notification.getCreatedAt()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving notification: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get notification by ID
     */
    public Notification getNotificationById(String notificationId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_NOTIFICATION_BY_ID)) {
            
            pstmt.setString(1, notificationId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToNotification(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting notification by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all notifications
     */
    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQLConstants.SELECT_ALL_NOTIFICATIONS)) {
            
            while (rs.next()) {
                notifications.add(mapResultSetToNotification(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all notifications: " + e.getMessage());
        }
        return notifications;
    }
    
    /**
     * Get notifications by user ID
     */
    public List<Notification> getNotificationsByUserId(String userId) {
        List<Notification> notifications = new ArrayList<>();
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_NOTIFICATIONS_BY_USER_ID)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                notifications.add(mapResultSetToNotification(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting notifications by user ID: " + e.getMessage());
        }
        return notifications;
    }
    
    /**
     * Get unread notifications by user ID
     */
    public List<Notification> getUnreadNotificationsByUserId(String userId) {
        List<Notification> notifications = new ArrayList<>();
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.SELECT_UNREAD_NOTIFICATIONS_BY_USER_ID)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                notifications.add(mapResultSetToNotification(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting unread notifications: " + e.getMessage());
        }
        return notifications;
    }
    
    /**
     * Get count of unread notifications by user ID
     */
    public int getUnreadNotificationCount(String userId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.COUNT_UNREAD_NOTIFICATIONS)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error getting unread notification count: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Mark notification as read
     */
    public boolean markAsRead(String notificationId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.MARK_NOTIFICATION_AS_READ)) {
            
            pstmt.setString(1, notificationId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error marking notification as read: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Mark all notifications as read for a user
     */
    public boolean markAllAsReadForUser(String userId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.MARK_ALL_NOTIFICATIONS_AS_READ)) {
            
            pstmt.setString(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error marking all notifications as read: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete notification by ID
     */
    public boolean deleteNotification(String notificationId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DELETE_NOTIFICATION)) {
            
            pstmt.setString(1, notificationId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting notification: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete all notifications for a user
     */
    public boolean deleteAllNotificationsForUser(String userId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQLConstants.DELETE_ALL_NOTIFICATIONS_BY_USER_ID)) {
            
            pstmt.setString(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting all notifications for user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete old read notifications (older than specified days)
     */
    public boolean deleteOldReadNotifications(int daysOld) {
        String sql = "DELETE FROM notifications WHERE is_read = TRUE AND created_at < DATE_SUB(NOW(), INTERVAL ? DAY)";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, daysOld);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " old notifications");
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error deleting old notifications: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Map ResultSet to Notification object
     */
    private Notification mapResultSetToNotification(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setNotificationId(rs.getString("notification_id"));
        notification.setUserId(rs.getString("user_id"));
        notification.setUserType(rs.getString("user_type"));
        notification.setTitle(rs.getString("title"));
        notification.setMessage(rs.getString("message"));
        notification.setRead(rs.getBoolean("is_read"));
        notification.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return notification;
    }
}
