package com.flipfit.bean;

public class GymAdmin extends User{
	public GymAdmin() {
		super();
	}

	public GymAdmin(String userID, String name, String email, String phoneNumber, String city, String password) {
		super(userID, name, email, phoneNumber, city, password);
	}
	
}
