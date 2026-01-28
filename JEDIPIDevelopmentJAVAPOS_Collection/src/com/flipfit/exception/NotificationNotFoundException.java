package com.flipfit.exception;

/**
 * Thrown when a notification cannot be located by id.
 */
public class NotificationNotFoundException extends Exception {
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
