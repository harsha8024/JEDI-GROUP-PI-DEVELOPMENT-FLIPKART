// TODO: Auto-generated Javadoc
package com.flipfit.bean;

import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * The Class Booking.
 *
 * @author Aman
 * @ClassName "Booking"
 */
public class Booking {

	/** The booking id. */
	private String bookingId;

	/** The user id. */
	private String userId;

	/** The slot id. */
	private String slotId;

	/** The gym id. */
	private String gymId;

	/** The booking date. */
	private LocalDate bookingDate;

	/** The status. */
	private String status;

	/** The created at. */
	private LocalDateTime createdAt;

	/** The booking details. */
	private BookingDetails bookingDetails;

	/**
	 * Instantiates a new booking.
	 */
	public Booking() {

	}

	/**
	 * Instantiates a new booking with the specified details.
	 *
	 * @param bookingId      the unique identifier for the booking
	 * @param userId         the identifier of the user who made the booking
	 * @param slotId         the identifier of the gym slot booked
	 * @param gymId          the identifier of the gym where the booking is made
	 * @param bookingDate    the date of the booking
	 * @param status         the current status of the booking
	 * @param createdAt      the timestamp when the booking was created
	 * @param bookingDetails additional details related to the booking
	 */
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
	 * Gets the slot id.
	 *
	 * @return the slot id
	 */
	public String getSlotId() {
		return slotId;
	}

	/**
	 * Sets the slot id.
	 *
	 * @param slotId the new slot id
	 */
	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}

	/**
	 * Gets the gym id.
	 *
	 * @return the gym id
	 */
	public String getGymId() {
		return gymId;
	}

	/**
	 * Sets the gym id.
	 *
	 * @param gymId the new gym id
	 */
	public void setGymId(String gymId) {
		this.gymId = gymId;
	}

	/**
	 * Gets the booking date.
	 *
	 * @return the booking date
	 */
	public LocalDate getBookingDate() {
		return bookingDate;
	}

	/**
	 * Sets the booking date.
	 *
	 * @param bookingDate the new booking date
	 */
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the booking details.
	 *
	 * @return the booking details
	 */
	public BookingDetails getBookingDetails() {
		return bookingDetails;
	}

	/**
	 * Sets the booking details.
	 *
	 * @param bookingDetails the new booking details
	 */
	public void setBookingDetails(BookingDetails bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

	/**
	 * Returns a string representation of the booking.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", userId=" + userId + ", slotId=" + slotId + ", gymId=" + gymId
				+ ", bookingDate=" + bookingDate + ", status=" + status + ", createdAt=" + createdAt + "]";
	}

}
