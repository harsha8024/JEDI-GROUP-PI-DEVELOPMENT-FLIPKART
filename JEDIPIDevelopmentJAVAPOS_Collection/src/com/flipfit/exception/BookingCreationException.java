package com.flipfit.exception;

/**
 * Thrown when creating a booking fails.
 */
public class BookingCreationException extends Exception {
    public BookingCreationException(String message) {
        super(message);
    }

    public BookingCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
