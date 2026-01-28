package com.flipfit.exception;

/**
 * Thrown when payment details can't be found for a given id.
 */
public class PaymentNotFoundException extends Exception {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
