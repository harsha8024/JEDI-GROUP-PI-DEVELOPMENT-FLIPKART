package com.flipfit.business;

import com.flipfit.bean.Notification;
import com.flipfit.dao.NotificationDAO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class NotificationServiceImpl implements NotificationInterface {

    private final NotificationDAO notificationDAO = new NotificationDAO();

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

    @Override
    public List<Notification> getUnreadNotifications(String userId) {
        return notificationDAO.getUnreadNotificationsByUserId(userId);
    }

    @Override
    public void markAsRead(String notificationId) {
        notificationDAO.markAsRead(notificationId);
    }
}