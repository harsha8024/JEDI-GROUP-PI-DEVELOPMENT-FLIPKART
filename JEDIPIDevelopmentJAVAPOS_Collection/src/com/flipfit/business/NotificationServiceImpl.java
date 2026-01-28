// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Notification;
import com.flipfit.dao.NotificationDAO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The Class NotificationServiceImpl.
 * Implementation of NotificationInterface that provides notification management functionality.
 *
 * @author team pi
 * @ClassName "NotificationServiceImpl"
 */
public class NotificationServiceImpl implements NotificationInterface {

    /** The notification DAO. */
    private final NotificationDAO notificationDAO = new NotificationDAO();

    /**
     * Send notification.
     * Creates and sends a notification to the specified user.
     *
     * @param userId the user id
     * @param message the message
     * @param title the title
     */
    @Override
    public void sendNotification(String userId, String message, String title) {
        Notification notification = new Notification();
        notification.setNotificationId(UUID.randomUUID().toString());
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setTitle(title);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        
        notificationDAO.saveNotification(notification);
        System.out.println("Notification sent to user " + userId + ": " + title);
    }

    /**
     * Gets the unread notifications.
     *
     * @param userId the user id
     * @return the unread notifications
     */
    @Override
    public List<Notification> getUnreadNotifications(String userId) {
        return notificationDAO.getUnreadNotificationsByUserId(userId);
    }

    /**
     * Mark notification as read.
     *
     * @param notificationId the notification id
     */
    @Override
    public void markAsRead(String notificationId) {
        notificationDAO.markAsRead(notificationId);
    }
}