package com.flipkart.business;

public class PaymentServiceImpl implements PaymentService {
    @Override
    public boolean makePayment(String userId, double amount) {
        // Logic: Simulate a bank transaction and return success
        System.out.println("Payment of " + amount + " processed for user: " + userId);
        return true;
    }
}