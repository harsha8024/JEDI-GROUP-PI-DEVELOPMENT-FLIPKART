package com.flipfit.bean;

public class GymAdmin extends User{
	public GymAdmin() {
		super();
		this.setActive(true);
	}

	public GymAdmin(String userID, String name, String email, String phoneNumber, String city, String password,Role role,boolean isActive) {
		super(userID, name, email, phoneNumber, city, password, role, isActive);
		this.setActive(true);
	}
	
	
}
