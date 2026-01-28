// TODO: Auto-generated Javadoc
package com.flipfit.business;

import com.flipfit.bean.Payment;
import com.flipfit.dao.PaymentDAO;
import com.flipfit.dao.BookingDAO;
import java.math.BigDecimal;
import com.flipfit.exception.InvalidInputException;
import com.flipfit.exception.PaymentFailedException;
import com.flipfit.exception.PaymentNotFoundException;

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
    public boolean processPayment(String bookingId, double amount, String paymentMethod) throws InvalidInputException, PaymentFailedException {
        if (bookingId == null || bookingId.isBlank() || amount <= 0 || paymentMethod == null || paymentMethod.isBlank()) {
            throw new InvalidInputException("Invalid payment parameters.");
        }
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
            
            boolean saved = paymentDAO.savePayment(payment);
            if (!saved) throw new PaymentFailedException("Failed to save payment for booking: " + bookingId);
            return true;
        }
        throw new PaymentFailedException("Payment gateway returned failure.");
    }

    @Override
    public Payment getPaymentDetails(String paymentId) throws InvalidInputException, PaymentNotFoundException {
        if (paymentId == null || paymentId.isBlank()) throw new InvalidInputException("Invalid payment id.");
        Payment payment = paymentDAO.getPaymentById(paymentId);
        if (payment == null) throw new PaymentNotFoundException("Payment not found: " + paymentId);
        return payment;
    }

    @Override
    public boolean refundPayment(String paymentId) throws InvalidInputException, PaymentNotFoundException, PaymentFailedException {
        if (paymentId == null || paymentId.isBlank()) throw new InvalidInputException("Invalid payment id.");
        Payment payment = paymentDAO.getPaymentById(paymentId);
        if (payment == null) throw new PaymentNotFoundException("Payment not found: " + paymentId);
        // Logic to initiate refund
        boolean updated = paymentDAO.updatePaymentStatus(paymentId, "REFUNDED");
        if (!updated) throw new PaymentFailedException("Refund failed for payment: " + paymentId);
        return true;
    }
}