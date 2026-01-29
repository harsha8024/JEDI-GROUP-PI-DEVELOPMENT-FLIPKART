package com.flipfit.exception;

/**
 * Thrown when a booking with the given identifier cannot be found.
 */
public class BookingNotFoundException extends Exception {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
