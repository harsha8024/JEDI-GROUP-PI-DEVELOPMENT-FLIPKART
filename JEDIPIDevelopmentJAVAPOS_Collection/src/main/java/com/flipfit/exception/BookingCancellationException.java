package com.flipfit.exception;

/**
 * Thrown when cancellation of a booking fails.
 */
public class BookingCancellationException extends Exception {
    public BookingCancellationException(String message) {
        super(message);
    }

    public BookingCancellationException(String message, Throwable cause) {
        super(message, cause);
    }
}
