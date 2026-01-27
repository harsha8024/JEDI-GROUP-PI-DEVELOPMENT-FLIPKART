// TODO: Auto-generated Javadoc
package com.flipfit.bean;

/**
 * The Class GymOwner.
 *
 * @author team pi
 * @ClassName "GymOwner"
 */
public class GymOwner extends User {

	/** The pan number. */
	private String panNumber;

	/** The aadhar number. */
	private String aadharNumber;

	/** The registration. */
	private Registration registration;

	/**
	 * Instantiates a new gym owner.
	 */
	public GymOwner() {
		super();
	}

	/**
	 * Instantiates a new gym owner with the specified details.
	 *
	 * @param userID       the user ID
	 * @param name         the name
	 * @param email        the email
	 * @param phoneNumber  the phone number
	 * @param city         the city
	 * @param password     the password
	 * @param role         the role
	 * @param isActive     the active status
	 * @param panNumber    the pan number
	 * @param aadharNumber the aadhar number
	 */
	public GymOwner(String userID, String name, String email, String phoneNumber, String city, String password,
			Role role, boolean isActive, String panNumber, String aadharNumber) {
		super(userID, name, email, phoneNumber, city, password, role, isActive);
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
	}

	/**
	 * Gets the pan number.
	 *
	 * @return the pan number
	 */
	public String getPanNumber() {
		return panNumber;
	}

	/**
	 * Sets the pan number.
	 *
	 * @param panNumber the new pan number
	 */
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	/**
	 * Gets the aadhar number.
	 *
	 * @return the aadhar number
	 */
	public String getAadharNumber() {
		return aadharNumber;
	}

	/**
	 * Sets the aadhar number.
	 *
	 * @param aadharNumber the new aadhar number
	 */
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	/**
	 * Gets the registration.
	 *
	 * @return the registration
	 */
	public Registration getRegistration() {
		return registration;
	}

	/**
	 * Sets the registration.
	 *
	 * @param registration the new registration
	 */
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

}
