package com.flipfit.business;

import com.flipfit.bean.Notification;
import java.util.List;

public interface NotificationInterface {
    void sendNotification(String userId, String message, String title);
    List<Notification> getUnreadNotifications(String userId);
    void markAsRead(String notificationId);
}