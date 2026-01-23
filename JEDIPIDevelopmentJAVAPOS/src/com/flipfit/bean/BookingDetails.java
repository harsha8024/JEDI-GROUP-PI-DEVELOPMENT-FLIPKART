package com.flipfit.bean;

public class BookingDetails {
	
	private String detailsId;
	private String slotCode;
	private String specialRequirements;
	
	public BookingDetails() {
		
	}

	public BookingDetails(String detailsId, String slotCode, String specialRequirements) {
		super();
		this.detailsId = detailsId;
		this.slotCode = slotCode;
		this.specialRequirements = specialRequirements;
	}

	public String getDetailsId() {
		return detailsId;
	}

	public void setDetailsId(String detailsId) {
		this.detailsId = detailsId;
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
	
	
	
}
