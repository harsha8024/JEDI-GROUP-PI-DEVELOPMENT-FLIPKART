package com.flipfit.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
	
	private String paymentId;
	private String bookingId;
	private String userId;
	private BigDecimal amount;
	private String paymentMethod;
	private String paymentStatus;
	private LocalDateTime paymentDate;
	
	public Payment() {
		this.paymentDate = LocalDateTime.now();
		this.paymentStatus = "SUCCESS";
	}
	
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
	
	public String getPaymentId() {
		return paymentId;
	}
	
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	public String getBookingId() {
		return bookingId;
	}
	
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}
	
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}
	
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	// Backward compatibility - keeping old methods
	public String getStatus() {
		return paymentStatus;
	}
	
	public void setStatus(String status) {
		this.paymentStatus = status;
	}
	
	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", bookingId=" + bookingId + ", userId=" + userId + 
				", amount=" + amount + ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + 
				", paymentDate=" + paymentDate + "]";
	}
}
