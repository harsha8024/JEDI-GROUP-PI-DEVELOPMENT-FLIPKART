// TODO: Auto-generated Javadoc
package com.flipfit.bean;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * The Class Slot.
 *
 * @author team pi
 * @ClassName "Slot"
 */
public class Slot {

	/** The slot id. */
	private String slotId;

	/** The gym id. */
	private String gymId;

	/** The start time. */
	private LocalTime startTime;

	/** The end time. */
	private LocalTime endTime;

	/** The capacity. */
	private int capacity;

	/** The available seats. */
	private int availableSeats;

	/** The is approved. */
	private boolean isApproved;

	/** The price per booking. */
	private BigDecimal price;

	/**
	 * Instantiates a new slot.
	 */
	public Slot() {

	}

	/**
	 * Instantiates a new slot with the specified parameters.
	 *
	 * @param slotId         the unique identifier for the slot
	 * @param gymId          the identifier of the gym this slot belongs to
	 * @param startTime      the start time of the slot
	 * @param endTime        the end time of the slot
	 * @param capacity       the maximum capacity of the slot
	 * @param availableSeats the number of available seats in the slot
	 */
	public Slot(String slotId, String gymId, LocalTime startTime, LocalTime endTime, int capacity, int availableSeats) {
		super();
		this.slotId = slotId;
		this.gymId = gymId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity;
		this.availableSeats = availableSeats;
		this.isApproved = false;
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
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 * Sets the end time.
	 *
	 * @param endTime the new end time
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * Gets the capacity.
	 *
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Sets the capacity.
	 *
	 * @param capacity the new capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Gets the available seats.
	 *
	 * @return the available seats
	 */
	public int getAvailableSeats() {
		return availableSeats;
	}

	/**
	 * Sets the available seats.
	 *
	 * @param availableSeats the new available seats
	 */
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	/**
	 * Checks if is approved.
	 *
	 * @return true, if is approved
	 */
	public boolean isApproved() {
		return isApproved;
	}

	/**
	 * Sets the approved status.
	 *
	 * @param isApproved the new approved status
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * Returns a string representation of the slot.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return "Slot [slotId=" + slotId + ", gymId=" + gymId + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", capacity=" + capacity + ", availableSeats=" + availableSeats + ", isApproved=" + isApproved 
				+ ", price=" + price + "]";
	}

}
