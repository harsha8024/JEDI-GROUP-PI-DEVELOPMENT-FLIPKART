package com.flipfit.business;

import com.flipfit.bean.Payment;

public interface PaymentInterface {
    boolean processPayment(String bookingId, double amount, String paymentMethod);
    Payment getPaymentDetails(String paymentId);
    boolean refundPayment(String paymentId);
}