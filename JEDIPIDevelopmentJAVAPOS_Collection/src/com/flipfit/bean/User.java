package com.flipfit.bean;

public class User {
	private String userID;
	private String name;
	private String email;
	private String phoneNumber;
	private String city;
	private String password;
	private Role role;
	private boolean isActive;
	
	public User() {
		this.isActive=false;
	}
	
	
	
	public User(String userID, String name, String email, String phoneNumber, String city, String password, Role role,
			boolean isActive) {
		super();
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.password = password;
		this.role = role;
		this.isActive = false;
	}



	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}



	public Role getRole() {
		return role;
	}



	public void setRole(Role role) {
		this.role = role;
	}



	public boolean isActive() {
		return isActive;
	}



	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	
}
