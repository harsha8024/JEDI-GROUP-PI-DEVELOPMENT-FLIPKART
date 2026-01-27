package com.flipfit.bean;

public class GymOwner extends User{
	
	private String panNumber;
	private String aadharNumber;
	private Registration registration;
	
	public GymOwner() {
		super();
	}

	public GymOwner(String userID, String name, String email, String phoneNumber, String city, String password,
			Role role,boolean isActive,String panNumber, String aadharNumber) {
		super(userID, name, email, phoneNumber, city, password,role, isActive);
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	
	
	
}
