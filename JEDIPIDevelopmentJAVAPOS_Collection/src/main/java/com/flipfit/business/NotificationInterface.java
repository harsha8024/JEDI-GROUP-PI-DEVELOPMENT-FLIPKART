package com.flipfit.business;

import com.flipfit.bean.Notification;
import java.util.List;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.NotificationFailedException;
import com.flipfit.exception.NotificationNotFoundException;

public interface NotificationInterface {
    void sendNotification(String userId, String message, String title) throws InvalidInputException, NotificationFailedException;
    List<Notification> getUnreadNotifications(String userId) throws InvalidInputException;
    void markAsRead(String notificationId) throws InvalidInputException, NotificationNotFoundException;
}