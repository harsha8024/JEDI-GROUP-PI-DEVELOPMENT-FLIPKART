package com.flipfit.exception;

/**
 * Thrown when a gym cannot be found for the given identifier or query.
 */
public class GymNotFoundException extends Exception {
    public GymNotFoundException(String message) {
        super(message);
    }
}
