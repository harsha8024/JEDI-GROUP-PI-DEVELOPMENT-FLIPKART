// TODO: Auto-generated Javadoc
package com.flipfit.bean;

/**
 * The Class GymCustomer.
 *
 * @author team pi
 * @ClassName "GymCustomer"
 */
public class GymCustomer extends User {

	/** The registration details. */
	private Registration registration;

	/**
	 * Instantiates a new gym customer.
	 */
	public GymCustomer() {
		super();
	}

	/**
	 * Instantiates a new gym customer with the specified details.
	 *
	 * @param userID      the user ID
	 * @param name        the name
	 * @param email       the email
	 * @param phoneNumber the phone number
	 * @param city        the city
	 * @param password    the password
	 * @param role        the role
	 * @param isApproved  the active status
	 */
	public GymCustomer(String userID, String name, String email, String phoneNumber, String city, String password,
			Role role, boolean isApproved) {
		super(userID, name, email, phoneNumber, city, password, role, isApproved);
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
