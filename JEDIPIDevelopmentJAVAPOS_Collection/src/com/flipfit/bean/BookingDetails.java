// TODO: Auto-generated Javadoc
package com.flipfit.bean;

/**
 * The Class BookingDetails.
 *
 * @author team pi
 * @ClassName "BookingDetails"
 */
public class BookingDetails {

	/** The details id. */
	private String detailsId;

	/** The booking id. */
	private String bookingId;

	/** The slot code. */
	private String slotCode;

	/** The special requirements. */
	private String specialRequirements;

	/** The is waitlisted. */
	private boolean isWaitlisted;

	/** The waitlist position. */
	private int waitlistPosition;

	/**
	 * Instantiates a new booking details.
	 */
	public BookingDetails() {
		this.isWaitlisted = false;
		this.waitlistPosition = 0;
	}

	/**
	 * Instantiates a new booking details with the specified parameters.
	 *
	 * @param detailsId           the unique identifier for the booking details
	 * @param bookingId           the identifier of the associated booking
	 * @param slotCode            the code representing the gym slot
	 * @param specialRequirements any specific requirements for the booking
	 * @param isWaitlisted        whether the booking is on the waitlist
	 * @param waitlistPosition    the position in the waitlist if applicable
	 */
	public BookingDetails(String detailsId, String bookingId, String slotCode, String specialRequirements,
			boolean isWaitlisted, int waitlistPosition) {
		super();
		this.detailsId = detailsId;
		this.bookingId = bookingId;
		this.slotCode = slotCode;
		this.specialRequirements = specialRequirements;
		this.isWaitlisted = isWaitlisted;
		this.waitlistPosition = waitlistPosition;
	}

	/**
	 * Gets the details id.
	 *
	 * @return the details id
	 */
	public String getDetailsId() {
		return detailsId;
	}

	/**
	 * Sets the details id.
	 *
	 * @param detailsId the new details id
	 */
	public void setDetailsId(String detailsId) {
		this.detailsId = detailsId;
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
	 * Gets the slot code.
	 *
	 * @return the slot code
	 */
	public String getSlotCode() {
		return slotCode;
	}

	/**
	 * Sets the slot code.
	 *
	 * @param slotCode the new slot code
	 */
	public void setSlotCode(String slotCode) {
		this.slotCode = slotCode;
	}

	/**
	 * Gets the special requirements.
	 *
	 * @return the special requirements
	 */
	public String getSpecialRequirements() {
		return specialRequirements;
	}

	/**
	 * Sets the special requirements.
	 *
	 * @param specialRequirements the new special requirements
	 */
	public void setSpecialRequirements(String specialRequirements) {
		this.specialRequirements = specialRequirements;
	}

	/**
	 * Checks if is waitlisted.
	 *
	 * @return true, if is waitlisted
	 */
	public boolean isWaitlisted() {
		return isWaitlisted;
	}

	/**
	 * Sets the waitlisted status.
	 *
	 * @param isWaitlisted the new waitlisted status
	 */
	public void setWaitlisted(boolean isWaitlisted) {
		this.isWaitlisted = isWaitlisted;
	}

	/**
	 * Gets the waitlist position.
	 *
	 * @return the waitlist position
	 */
	public int getWaitlistPosition() {
		return waitlistPosition;
	}

	/**
	 * Sets the waitlist position.
	 *
	 * @param waitlistPosition the new waitlist position
	 */
	public void setWaitlistPosition(int waitlistPosition) {
		this.waitlistPosition = waitlistPosition;
	}

	/**
	 * Returns a string representation of the booking details.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return "BookingDetails [detailsId=" + detailsId + ", bookingId=" + bookingId + ", slotCode=" + slotCode
				+ ", specialRequirements=" + specialRequirements + ", isWaitlisted=" + isWaitlisted
				+ ", waitlistPosition=" + waitlistPosition + "]";
	}

}
