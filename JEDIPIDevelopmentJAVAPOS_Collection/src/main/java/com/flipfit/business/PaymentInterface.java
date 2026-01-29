package com.flipfit.business;

import com.flipfit.bean.Payment;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.PaymentFailedException;
import com.flipfit.exception.PaymentNotFoundException;

public interface PaymentInterface {
    boolean processPayment(String bookingId, double amount, String paymentMethod) throws InvalidInputException, PaymentFailedException;
    Payment getPaymentDetails(String paymentId) throws InvalidInputException, PaymentNotFoundException;
    boolean refundPayment(String paymentId) throws InvalidInputException, PaymentNotFoundException, PaymentFailedException;
}