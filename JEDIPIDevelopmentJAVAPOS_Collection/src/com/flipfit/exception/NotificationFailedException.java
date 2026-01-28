package com.flipfit.exception;

/**
 * Thrown when sending or updating a notification fails.
 */
public class NotificationFailedException extends Exception {
    public NotificationFailedException(String message) {
        super(message);
    }

    public NotificationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
