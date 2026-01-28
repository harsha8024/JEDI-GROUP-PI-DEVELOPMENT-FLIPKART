package com.flipfit.exception;

/**
 * Thrown when a slot update/delete operation fails.
 */
public class SlotOperationException extends Exception {
    public SlotOperationException(String message) {
        super(message);
    }

    public SlotOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
