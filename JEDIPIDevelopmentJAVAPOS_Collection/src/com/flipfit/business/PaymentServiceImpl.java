package com.flipfit.business;

import com.flipfit.bean.Payment;
import com.flipfit.dao.PaymentDAO;
import java.math.BigDecimal;
import java.util.UUID;

public class PaymentServiceImpl implements PaymentInterface {

    private final PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    public boolean processPayment(String bookingId, double amount, String paymentMethod) {
        // 1. Validate payment details
        // 2. Call external payment gateway (Simulated)
        boolean success = true; // Assume gateway success
        
        if(success) {
            Payment payment = new Payment();
            payment.setPaymentId(UUID.randomUUID().toString());
            payment.setBookingId(bookingId);
            payment.setAmount(BigDecimal.valueOf(amount));
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentStatus("SUCCESS");
            
            return paymentDAO.savePayment(payment);
        }
        return false;
    }

    @Override
    public Payment getPaymentDetails(String paymentId) {
        return paymentDAO.getPaymentById(paymentId);
    }

    @Override
    public boolean refundPayment(String paymentId) {
        // Logic to initiate refund
        return paymentDAO.updatePaymentStatus(paymentId, "REFUNDED");
    }
}