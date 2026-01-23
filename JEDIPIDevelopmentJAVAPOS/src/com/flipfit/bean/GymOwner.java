package com.flipfit.bean;

public class GymOwner extends User{
	
	private String panNumber;
	private String aadharNumber;
	
	public GymOwner() {
		super();
	}

	public GymOwner(String userID, String name, String email, String phoneNumber, String city, String password,
			String panNumber, String aadharNumber) {
		super(userID, name, email, phoneNumber, city, password);
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
	
}
