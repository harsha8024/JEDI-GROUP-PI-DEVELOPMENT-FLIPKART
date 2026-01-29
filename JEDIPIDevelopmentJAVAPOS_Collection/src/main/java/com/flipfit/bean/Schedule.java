// TODO: Auto-generated Javadoc
package com.flipfit.bean;

import java.time.LocalDate;

/**
 * The Class Schedule.
 *
 * @author team pi
 * @ClassName "Schedule"
 */
public class Schedule {

	/** The schedule id. */
	private String scheduleId;

	/** The date. */
	private LocalDate date;

	/** The is updated. */
	private boolean isUpdated;

	/**
	 * Instantiates a new schedule.
	 */
	public Schedule() {

	}

	/**
	 * Instantiates a new schedule with the specified parameters.
	 *
	 * @param scheduleId the unique identifier for the schedule
	 * @param date       the date of the schedule
	 * @param isUpdated  whether the schedule has been updated
	 */
	public Schedule(String scheduleId, LocalDate date, boolean isUpdated) {
		super();
		this.scheduleId = scheduleId;
		this.date = date;
		this.isUpdated = isUpdated;
	}

	/**
	 * Gets the schedule id.
	 *
	 * @return the schedule id
	 */
	public String getScheduleId() {
		return scheduleId;
	}

	/**
	 * Sets the schedule id.
	 *
	 * @param scheduleId the new schedule id
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Checks if is updated.
	 *
	 * @return true, if is updated
	 */
	public boolean isUpdated() {
		return isUpdated;
	}

	/**
	 * Sets the updated status.
	 *
	 * @param isUpdated the new updated status
	 */
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

}
