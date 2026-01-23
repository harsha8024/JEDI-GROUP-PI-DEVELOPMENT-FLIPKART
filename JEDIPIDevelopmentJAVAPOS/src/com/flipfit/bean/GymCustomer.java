package com.flipfit.bean;

public class GymCustomer extends User{
	private boolean isApproved;

	public GymCustomer() {
		super();
	}
	public GymCustomer(String userID, String name, String email, String phoneNumber, String city, String password,
			boolean isApproved) {
		super(userID, name, email, phoneNumber, city, password);
		this.isApproved = isApproved;
	}
	
	public boolean isApproved() {
		return isApproved;
	}
	
	public void setApproved(boolean approved) {
		isApproved=approved;
	}
	
	
}
