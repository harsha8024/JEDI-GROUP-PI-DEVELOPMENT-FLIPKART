package com.flipfit.bean;

import java.time.LocalDateTime;

public class Booking {

	private String bookingId;
	private String userId;
	private String status;
	private LocalDateTime createdAt;
	
	public Booking() {
		
	}

	public Booking(String bookingId, String userId, String status, LocalDateTime createdAt) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.status = status;
		this.createdAt = createdAt;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
