package com.flipfit.exception;

/**
 * Thrown when a slot cannot be found for a given identifier.
 */
public class SlotNotFoundException extends Exception {
    public SlotNotFoundException(String message) {
        super(message);
    }
}
