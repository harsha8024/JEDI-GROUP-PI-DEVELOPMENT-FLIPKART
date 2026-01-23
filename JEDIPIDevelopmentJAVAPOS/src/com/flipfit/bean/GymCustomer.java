package com.flipfit.bean;

public class GymCustomer extends User{
	
	private Registration registration;
	public GymCustomer() {
		super();
	}
	public GymCustomer(String userID, String name, String email, String phoneNumber, String city, String password,
			Role role,boolean isApproved) {
		super(userID, name, email, phoneNumber, city, password, role, isApproved);
	}
	public Registration getRegistration() {
		return registration;
	}
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	
	
	
	
}
