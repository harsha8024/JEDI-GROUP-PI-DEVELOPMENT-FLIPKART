package com.flipkart.business;

public interface PaymentService {
    boolean makePayment(String userId, double amount);
}