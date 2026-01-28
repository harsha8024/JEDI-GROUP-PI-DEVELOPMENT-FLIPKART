// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Payment;
import com.flipfit.dao.PaymentDAO;
import com.flipfit.dao.BookingDAO;
import java.math.BigDecimal;

/**
 * The Class PaymentServiceImpl.
 * Implementation of PaymentInterface that provides payment processing functionality.
 *
 * @author team pi
 * @ClassName "PaymentServiceImpl"
 */
public class PaymentServiceImpl implements PaymentInterface {

    /** The payment DAO. */
    private final PaymentDAO paymentDAO = new PaymentDAO();
    
    /** The booking DAO. */
    private final BookingDAO bookingDAO = new BookingDAO();

    /**
     * Process payment.
     * Processes payment for a booking and records the transaction.
     *
     * @param bookingId the booking id
     * @param amount the amount
     * @param paymentMethod the payment method
     * @return true, if successful
     */
    @Override
    public boolean processPayment(String bookingId, double amount, String paymentMethod) {
        // 1. Validate payment details
        // 2. Call external payment gateway (Simulated)
        boolean success = true; // Assume gateway success
        
        if(success) {
            // Get customer ID from booking
            String customerId = null;
            var booking = bookingDAO.getBookingById(bookingId);
            if (booking != null) {
                customerId = booking.getUserId();
            }
            
            Payment payment = new Payment();
            payment.setPaymentId(paymentDAO.generatePaymentId());
            payment.setBookingId(bookingId);
            payment.setUserId(customerId);
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