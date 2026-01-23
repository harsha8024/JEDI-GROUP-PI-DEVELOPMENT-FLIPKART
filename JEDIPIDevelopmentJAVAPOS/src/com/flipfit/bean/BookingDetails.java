package com.flipfit.bean;

public class BookingDetails {
	
	private String detailsId;
	private String bookingId;
	private String slotCode;
	private String specialRequirements;
	private boolean isWaitlisted;
	private int waitlistPosition;
	
	public BookingDetails() {
		this.isWaitlisted = false;
		this.waitlistPosition = 0;
	}

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

	public String getDetailsId() {
		return detailsId;
	}

	public void setDetailsId(String detailsId) {
		this.detailsId = detailsId;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getSlotCode() {
		return slotCode;
	}

	public void setSlotCode(String slotCode) {
		this.slotCode = slotCode;
	}

	public String getSpecialRequirements() {
		return specialRequirements;
	}

	public void setSpecialRequirements(String specialRequirements) {
		this.specialRequirements = specialRequirements;
	}

	public boolean isWaitlisted() {
		return isWaitlisted;
	}

	public void setWaitlisted(boolean isWaitlisted) {
		this.isWaitlisted = isWaitlisted;
	}

	public int getWaitlistPosition() {
		return waitlistPosition;
	}

	public void setWaitlistPosition(int waitlistPosition) {
		this.waitlistPosition = waitlistPosition;
	}

	@Override
	public String toString() {
		return "BookingDetails [detailsId=" + detailsId + ", bookingId=" + bookingId + ", slotCode=" + slotCode
				+ ", specialRequirements=" + specialRequirements + ", isWaitlisted=" + isWaitlisted
				+ ", waitlistPosition=" + waitlistPosition + "]";
	}
	
}
