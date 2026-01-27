// TODO: Auto-generated Javadoc
package com.flipfit.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The Class Payment.
 *
 * @author team pi
 * @ClassName "Payment"
 */
public class Payment {

	/** The payment id. */
	private String paymentId;

	/** The booking id. */
	private String bookingId;

	/** The user id. */
	private String userId;

	/** The amount. */
	private BigDecimal amount;

	/** The payment method. */
	private String paymentMethod;

	/** The payment status. */
	private String paymentStatus;

	/** The payment date. */
	private LocalDateTime paymentDate;

	/**
	 * Instantiates a new payment.
	 */
	public Payment() {
		this.paymentDate = LocalDateTime.now();
		this.paymentStatus = "SUCCESS";
	}

	/**
	 * Instantiates a new payment with the specified parameters.
	 *
	 * @param paymentId     the unique identifier for the payment
	 * @param bookingId     the identifier of the associated booking
	 * @param userId        the identifier of the user making the payment
	 * @param amount        the amount paid
	 * @param paymentMethod the method of payment
	 * @param paymentStatus the status of the payment
	 * @param paymentDate   the timestamp when the payment was made
	 */
	public Payment(String paymentId, String bookingId, String userId, BigDecimal amount,
			String paymentMethod, String paymentStatus, LocalDateTime paymentDate) {
		super();
		this.paymentId = paymentId;
		this.bookingId = bookingId;
		this.userId = userId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
	}

	/**
	 * Gets the payment id.
	 *
	 * @return the payment id
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * Sets the payment id.
	 *
	 * @param paymentId the new payment id
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * Gets the booking id.
	 *
	 * @return the booking id
	 */
	public String getBookingId() {
		return bookingId;
	}

	/**
	 * Sets the booking id.
	 *
	 * @param bookingId the new booking id
	 */
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the payment method.
	 *
	 * @return the payment method
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method.
	 *
	 * @param paymentMethod the new payment method
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Gets the payment status.
	 *
	 * @return the payment status
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * Sets the payment status.
	 *
	 * @param paymentStatus the new payment status
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * Gets the payment date.
	 *
	 * @return the payment date
	 */
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	/**
	 * Sets the payment date.
	 *
	 * @param paymentDate the new payment date
	 */
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	// Backward compatibility - keeping old methods
	public String getStatus() {
		return paymentStatus;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.paymentStatus = status;
	}

	/**
	 * Returns a string representation of the payment.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", bookingId=" + bookingId + ", userId=" + userId +
				", amount=" + amount + ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus +
				", paymentDate=" + paymentDate + "]";
	}
}
