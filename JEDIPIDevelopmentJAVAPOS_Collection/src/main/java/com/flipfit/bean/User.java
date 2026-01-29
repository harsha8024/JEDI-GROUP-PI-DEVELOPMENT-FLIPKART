// TODO: Auto-generated Javadoc
package com.flipfit.bean;

/**
 * The Class User.
 *
 * @author team pi
 * @ClassName "User"
 */
public class User {

	/** The user ID. */
	private String userID;

	/** The name. */
	private String name;

	/** The email. */
	private String email;

	/** The phone number. */
	private String phoneNumber;

	/** The city. */
	private String city;

	/** The password. */
	private String password;

	/** The role. */
	private Role role;

	/** The is active. */
	private boolean isActive;

	/**
	 * Instantiates a new user.
	 */
	public User() {
		this.isActive = false;
	}

	/**
	 * Instantiates a new user with the specified details.
	 *
	 * @param userID      the user ID
	 * @param name        the name of the user
	 * @param email       the email address of the user
	 * @param phoneNumber the phone number of the user
	 * @param city        the city where the user resides
	 * @param password    the password for the user's account
	 * @param role        the role assigned to the user
	 * @param isActive    the active status of the user
	 */
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

	/**
	 * Gets the user ID.
	 *
	 * @return the user ID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * Sets the user ID.
	 *
	 * @param userID the new user ID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
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
