package com.flipfit.exception;

/**
 * Thrown when the provided date range / date format is invalid.
 */
public class InvalidDateRangeException extends Exception {
    public InvalidDateRangeException(String message) {
        super(message);
    }

    public InvalidDateRangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
