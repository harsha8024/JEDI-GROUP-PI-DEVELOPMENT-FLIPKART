package com.flipfit.exception;

/**
 * Thrown when trying to create a registration that already exists for a user.
 */
public class RegistrationAlreadyExistsException extends Exception {
    public RegistrationAlreadyExistsException(String message) {
        super(message);
    }
}
