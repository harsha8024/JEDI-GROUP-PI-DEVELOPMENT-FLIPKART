package com.flipfit.exception;

/**
 * Thrown when a payment cannot be processed successfully.
 */
public class PaymentFailedException extends Exception {
    public PaymentFailedException(String message) {
        super(message);
    }

    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
