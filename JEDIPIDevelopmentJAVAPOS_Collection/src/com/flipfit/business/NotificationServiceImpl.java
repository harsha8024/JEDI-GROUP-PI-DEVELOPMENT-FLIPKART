// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Notification;
import com.flipfit.dao.NotificationDAO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.NotificationFailedException;
import com.flipfit.exception.NotificationNotFoundException;

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
    public void sendNotification(String userId, String message, String title) throws InvalidInputException, NotificationFailedException {
        if (userId == null || userId.isBlank() || message == null || message.isBlank() || title == null || title.isBlank()) {
            throw new InvalidInputException("Invalid notification parameters.");
        }

        Notification notification = new Notification();
        notification.setNotificationId(UUID.randomUUID().toString());
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setTitle(title);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        
        boolean saved = notificationDAO.saveNotification(notification);
        if (!saved) throw new NotificationFailedException("Failed to send notification to user: " + userId);
        System.out.println("Notification sent to user " + userId + ": " + title);
    }

    /**
     * Gets the unread notifications.
     *
     * @param userId the user id
     * @return the unread notifications
     */
    @Override
    public List<Notification> getUnreadNotifications(String userId) throws InvalidInputException {
        if (userId == null || userId.isBlank()) throw new InvalidInputException("User ID must be provided.");
        return notificationDAO.getUnreadNotificationsByUserId(userId);
    }

    /**
     * Mark notification as read.
     *
     * @param notificationId the notification id
     */
    @Override
    public void markAsRead(String notificationId) throws InvalidInputException, NotificationNotFoundException {
        if (notificationId == null || notificationId.isBlank()) throw new InvalidInputException("Notification ID must be provided.");
        boolean result = notificationDAO.markAsRead(notificationId);
        if (!result) throw new NotificationNotFoundException("Notification not found: " + notificationId);
    }
}