package com.flipfit.bean;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class Booking {

	private String bookingId;
	private String userId;
	private String slotId;
	private String gymId;
	private LocalDate bookingDate;
	private String status;
	private LocalDateTime createdAt;
	private BookingDetails bookingDetails;
	
	public Booking() {
		
	}

	public Booking(String bookingId, String userId, String slotId, String gymId, LocalDate bookingDate, 
			String status, LocalDateTime createdAt, BookingDetails bookingDetails) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.slotId = slotId;
		this.gymId = gymId;
		this.bookingDate = bookingDate;
		this.status = status;
		this.createdAt = createdAt;
		this.bookingDetails = bookingDetails;
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

	public String getSlotId() {
		return slotId;
	}

	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}

	public String getGymId() {
		return gymId;
	}

	public void setGymId(String gymId) {
		this.gymId = gymId;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
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

	public BookingDetails getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(BookingDetails bookingDetails) {
		this.bookingDetails = bookingDetails;
	}
	
	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", userId=" + userId + ", slotId=" + slotId + ", gymId=" + gymId
				+ ", bookingDate=" + bookingDate + ", status=" + status + ", createdAt=" + createdAt + "]";
	}
	
}
